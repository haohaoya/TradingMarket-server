package top.hhduan.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import top.hhduan.market.bo.*;
import top.hhduan.market.constants.RedisConstants;
import top.hhduan.market.constants.VerifyCodeConstants;
import top.hhduan.market.entity.User;
import top.hhduan.market.enums.CodeEnum;
import top.hhduan.market.exception.BusinessException;
import top.hhduan.market.mapper.UserMapper;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.*;
import top.hhduan.market.vo.LoginVO;
import top.hhduan.market.vo.TokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/7 14:50
 * @description:
 * @version: 1.0
 */
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册接口
     * @param file
     * @param bo
     */
    @Override
    public void register(MultipartFile file, RegisterBO bo) {
        String key = String.format(RedisConstants.REGISTER_VERIFY_CODE, bo.getMobile());
        // 获取缓存中的验证码
        String code = RedisUtils.get(key);
        Assertion.notEmpty(code, "验证码已失效");
        Assertion.equals(bo.getVerifyCode(), code, "验证码错误");
        // 判断用户是否已经注册
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", bo.getMobile());
        List<User> list = userMapper.selectByExample(example);
        Assertion.isEmpty(list, "手机号已注册");
        example.clear();
        criteria = example.createCriteria();
        // 判断昵称是否已存在
        criteria.andEqualTo("userName", bo.getUserName());
        List<User> userNames = userMapper.selectByExample(example);
        Assertion.isEmpty(userNames, "昵称已存在");
        // 上传头像 oss存储
        OSSClient ossClient = OssUtil.getOssClient();
        String bucketName = OssUtil.getBucketName();
        String diskName = "images/user/" + DateUtils.getTimeString(new Date());
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        StringBuilder stringBuilder = null;
        boolean upload = false;
        try {
            upload = OssUtil.uploadInputStreamObject2Oss(ossClient, file.getInputStream(), fileName, bucketName, diskName);
        } catch (Exception e) {
            Assertion.isTrue(true, "头像上传失败");
        }
        if (upload) {
            // 用户头像地址
            String userAvatar = OssUtil.getOssUrl() + "/" + diskName + "/" + fileName;
            // 存储用户信息
            User user = new User();
            Date date = new Date();
            BeanUtils.copyProperties(bo, user);
            user.setUserId(UUIDUtils.getUid());
            user.setPassword(Md5Utils.md5(user.getPassword()));
            user.setCreateTime(date);
            user.setUpdateTime(date);
            user.setUserAvatar(userAvatar);
            userMapper.insertSelective(user);
            RedisUtils.del(key);
        } else {
            log.info("注册用户头像上传失败");
        }

    }

    @Override
    public LoginVO login(LoginBO bo) {
        // 判断用户是否存在
        User old = checkUserExsits(bo.getMobile());
        Assertion.notNull(old, "用户不存在");
        Assertion.equals(old.getPassword(), Md5Utils.md5(bo.getPassword()), "密码不正确");
        Assertion.equals(1, old.getStatus(), "用户已被锁定");
        // 已存在用户信息 封装修改实体
        User entity = new User();
        Date date = new Date();
        entity.setUserId(old.getUserId());
        entity.setLastLoginTime(date);
        entity.setLoginCounts(old.getLoginCounts() + 1);
        entity.setLoginStatus(1);
        entity.setUpdateTime(date);
        // 生成token
        String token = TokenUtils.getToken(old.getUserId());
        // token失效时间点
        Long tokenExpired = System.currentTimeMillis() + RedisConstants.TOKEN_EXPIRED * 1000;
        entity.setToken(token);
        entity.setTokenExpired(tokenExpired);
        userMapper.updateByPrimaryKeySelective(entity);
        // 封装token信息
        TokenVO tokenVO = new TokenVO();
        tokenVO.setUserId(old.getUserId());
        tokenVO.setMobile(old.getMobile());
        // 删除旧token
        RedisUtils.del(String.format(RedisConstants.TOKEN, old.getToken()));
        // 将token信息放入缓存 并设置7天有效期
        RedisUtils.setEx(String.format(RedisConstants.TOKEN, token), RedisConstants.TOKEN_EXPIRED, JSON.toJSONString(tokenVO));
        // 将最新用户信息放入缓存 并设置7天有效期
        BeanUtil.copyPropertiesIgnoreNull(entity, old);
        RedisUtils.setEx(String.format(RedisConstants.USER_INFO, bo.getMobile()), RedisConstants.USER_INFO_EXPIRE, JSON.toJSONString(old));
        // 封装返回客户端信息
        LoginVO vo = new LoginVO();
        BeanUtils.copyProperties(old, vo);
        vo.setLastLoginTime(date);
        vo.setToken(token);
        return vo;
    }

    /**
     * 查询用户是否存在
     *
     * @param mobile
     * @return
     */
    private User checkUserExsits(String mobile) {
        User old;
        // 判断用户是否存在 先查询缓存
        String info = RedisUtils.get(String.format(RedisConstants.USER_INFO, mobile));
        if (Detect.notEmpty(info)) {
            // 缓存中存在用户信息
            old = JSONObject.parseObject(info, User.class);
        } else {
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("mobile", mobile);
            Example.Criteria criteria = example.createCriteria().andEqualTo("userName", mobile);
            example.or(criteria);
            List<User> list = userMapper.selectByExample(example);
            Assertion.notEmpty(list, "用户不存在");
            // 判断密码是否正确
            old = list.get(0);
        }
        return old;
    }

    /**
     * 获取验证码
     * @param bo
     */
    @Override
    public void getVerifyCode(VerifyCodeBO bo) {
        String key;
        // 注册验证码
        if (VerifyCodeConstants.REGISTER == bo.getType()) {
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("mobile", bo.getMobile());
            User user = userMapper.selectOneByExample(example);
            Assertion.isNull(user, "手机已注册");
            key = String.format(RedisConstants.REGISTER_VERIFY_CODE, bo.getMobile());
        } else if (VerifyCodeConstants.RETRIEVE_PWD == bo.getType()) {
            // 找回密码验证码
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("mobile", bo.getMobile());
            User user = userMapper.selectOneByExample(example);
            Assertion.notNull(user, "手机号未注册");
            key = String.format(RedisConstants.RETRIEVE_PWD_VERIFY_CODE, bo.getMobile());
        } else if (VerifyCodeConstants.OLD_MOBILE == bo.getType()) {
            // 校验旧手机验证码
            User user = this.checkToken(bo.getToken());
            Assertion.notNull(user, "手机号未注册");
            Assertion.equals(user.getMobile(), bo.getMobile(), "手机号必须与注册手机号一致");
            key = String.format(RedisConstants.OLD_MOBILE_VERIFY_CODE, bo.getMobile());
        } else {
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("mobile", bo.getMobile());
            User user = userMapper.selectOneByExample(example);
            Assertion.isNull(user, "手机已注册");
            key = String.format(RedisConstants.NEW_MOBILE_VERIFY_CODE, bo.getMobile());
        }
        // TODO 待接入短信发送服务 int code = (int) ((Math.random() * 9 + 1) * 100000)
        int code = 123456;
        // 将短信验证码放入redis 并设置5分钟有效时间
        RedisUtils.setEx(key, RedisConstants.VERIFY_CODE_EXPIRE, String.valueOf(code));
        log.info("短信发送成功");
    }

    @Override
    public User checkToken(String token) {
        if (!Detect.notEmpty(token)) {
            throw new BusinessException(CodeEnum.TOKEN_FAILURE);
        }
        User user;
        // 查询token是否有效
        TokenVO vo = new TokenVO();
        String tokenInfo = RedisUtils.get(String.format(RedisConstants.TOKEN, token));
        if (Detect.notEmpty(tokenInfo)) {
            vo = JSONObject.parseObject(tokenInfo, TokenVO.class);
            // 查询用户信息
            String userInfo = RedisUtils.get(String.format(RedisConstants.USER_INFO, vo.getMobile()));
            if (!Detect.notEmpty(userInfo)) {
                user = this.findUserByToken(token);
                RedisUtils.setEx(String.format(RedisConstants.USER_INFO, vo.getMobile()), RedisConstants.USER_INFO_EXPIRE, JSON.toJSONString(user));
            } else {
                user = JSONObject.parseObject(userInfo, User.class);
            }
        } else {
            user = this.findUserByToken(token);
            vo.setMobile(user.getMobile());
            vo.setUserId(user.getUserId());
            int expired = (int) ((user.getTokenExpired() - System.currentTimeMillis()) / 1000);
            RedisUtils.setEx(String.format(RedisConstants.TOKEN, token), expired, JSON.toJSONString(vo));
        }
        Assertion.notNull(user, "用户异常");
        return user;
    }

    /**
     * 通过token查询数据库用户信息
     *
     * @param token
     * @return
     */
    private User findUserByToken(String token) {
        // 缓存没有继续查询数据库
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("token", token)
                .andGreaterThanOrEqualTo("tokenExpired", System.currentTimeMillis());
        List<User> list = userMapper.selectByExample(example);
        if (!Detect.notEmpty(list)) {
            // token失效
            throw new BusinessException(CodeEnum.TOKEN_FAILURE);
        }
        return Detect.firstOne(list);
    }

    @Override
    public void updatePwd(UpdatePwdBO bo) {
        User user = this.checkToken(bo.getToken());
        Assertion.equals(user.getPassword(), Md5Utils.md5(bo.getOldPwd()), "旧密码不正确");
        Assertion.notEquals(user.getPassword(), Md5Utils.md5(bo.getNewPwd()), "新密码不能与旧密码一样");
        User update = new User();
        Date date = new Date();
        update.setUserId(user.getUserId());
        update.setUpdateTime(date);
        update.setPassword(Md5Utils.md5(bo.getNewPwd()));
        BeanUtil.copyPropertiesIgnoreNull(update, user);
        this.updateUserInfo(update, user);

    }

    @Override
    public void forgotPwd(ForgotPwdBO bo) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("mobile", bo.getMobile());
        User user = userMapper.selectOneByExample(example);
        Assertion.notNull(user, "手机号未注册");
        String key = String.format(RedisConstants.RETRIEVE_PWD_VERIFY_CODE, bo.getMobile());
        // 获取缓存中的验证码
        String code = RedisUtils.get(key);
        Assertion.notEmpty(code, "验证码已失效");
        Assertion.equals(bo.getVerifyCode(), code, "验证码错误");
        User retrieve = new User();
        Date date = new Date();
        retrieve.setUserId(user.getUserId());
        retrieve.setPassword(Md5Utils.md5(bo.getNewPwd()));
        retrieve.setUpdateTime(date);
        BeanUtil.copyPropertiesIgnoreNull(retrieve, user);
        this.updateUserInfo(retrieve, user);
        RedisUtils.del(key);
    }

    /**
     * 更新用户信息
     *
     * @param update 待修改的信息
     * @param user   最新信息
     */
    private void updateUserInfo(User update, User user) {
        // 更新数据库
        userMapper.updateByPrimaryKeySelective(update);
        // 更新缓存redis
        RedisUtils.setEx(String.format(RedisConstants.USER_INFO, user.getMobile()), RedisConstants.USER_INFO_EXPIRE, JSON.toJSONString(user));
    }

    @Override
    public void logout(String token) {
        String info = RedisUtils.get(String.format(RedisConstants.TOKEN, token));
        if (!Detect.notEmpty(info)) {
            // token失效
            throw new BusinessException(CodeEnum.TOKEN_FAILURE);
        }
        TokenVO vo = JSONObject.parseObject(info, TokenVO.class);
        User user = new User();
        user.setUserId(vo.getUserId());
        user.setLoginStatus(2);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        // 删除redis token信息
        RedisUtils.del(String.format(RedisConstants.TOKEN, token));
        // 删除redis 用户信息
        RedisUtils.del(String.format(RedisConstants.USER_INFO, vo.getMobile()));
    }

    @Override
    public void updateUserInfo(MultipartFile file, UserInfoBO bo) {
        User user = this.checkToken(bo.getToken());
        // 判断用户昵称是否重复
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("userName", bo.getUserName())
                .andNotEqualTo("userId", user.getUserId());
        User u = userMapper.selectOneByExample(example);
        Assertion.isNull(u, "昵称已存在");
        User update = new User();
        if (null != file) {
            // 上传头像 oss存储
            OSSClient ossClient = OssUtil.getOssClient();
            String bucketName = OssUtil.getBucketName();
            String diskName = "images/user/" + DateUtils.getTimeString(new Date());
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            StringBuilder stringBuilder = null;
            try {
                OssUtil.uploadInputStreamObject2Oss(ossClient, file.getInputStream(), fileName, bucketName, diskName);
            } catch (Exception e) {
                Assertion.isTrue(true, "头像上传失败");
            }
            String userAvatar = OssUtil.getOssUrl() + "/" + diskName + "/" + fileName;
            if (Detect.notEmpty(userAvatar)) {
                update.setUserAvatar(userAvatar);
            }
        }
        if (Detect.notEmpty(bo.getUserName())) {
            update.setUserName(bo.getUserName());
        }
        update.setUserId(user.getUserId());
        BeanUtil.copyPropertiesIgnoreNull(update, user);
        this.updateUserInfo(update, user);
    }

    @Override
    public void verifyOldMobile(UpdateMobileBO bo) {
        User user = this.checkToken(bo.getToken());
        Assertion.equals(user.getMobile(), bo.getMobile(), "手机号必须与注册手机号一致");
        // 获取缓存中的验证码
        String code = RedisUtils.get(String.format(RedisConstants.OLD_MOBILE_VERIFY_CODE, bo.getMobile()));
        Assertion.notEmpty(code, "验证码已失效");
        Assertion.equals(bo.getVerifyCode(), code, "验证码错误");
    }

    @Override
    public void bindNewMobile(UpdateMobileBO bo) {
        User user = this.checkToken(bo.getToken());
        Assertion.notEquals(user.getMobile(), bo.getMobile(), "不能与原手机号一样");
        // 获取缓存中的验证码
        String code = RedisUtils.get(String.format(RedisConstants.NEW_MOBILE_VERIFY_CODE, bo.getMobile()));
        Assertion.notEmpty(code, "验证码已失效");
        Assertion.equals(bo.getVerifyCode(), code, "验证码错误");
        // 判断用户是否已经注册
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", bo.getMobile());
        List<User> list = userMapper.selectByExample(example);
        Assertion.isEmpty(list, "手机号已注册");
        User update = new User();
        update.setMobile(bo.getMobile());
        update.setUpdateTime(new Date());
        update.setUserId(user.getUserId());
        BeanUtil.copyPropertiesIgnoreNull(update, user);
        this.updateUserInfo(update, user);
        // 更新token信息
        TokenVO vo = new TokenVO();
        vo.setMobile(bo.getMobile());
        vo.setUserId(user.getUserId());
        int expired = (int) ((user.getTokenExpired() - System.currentTimeMillis()) / 1000);
        RedisUtils.setEx(String.format(RedisConstants.TOKEN, bo.getToken()), expired, JSON.toJSONString(vo));
    }
}
