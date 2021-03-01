package top.hhduan.market.service;

import top.hhduan.market.bo.*;
import top.hhduan.market.entity.User;
import top.hhduan.market.vo.LoginVO;
import org.springframework.web.multipart.MultipartFile;
import top.hhduan.market.bo.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/7 14:50
 * @description:
 * @version: 1.0
 */
public interface UserService extends BaseService<User, String> {

    /**
     * 注册业务
     * @param file
     * @param bo
     */
    void register(MultipartFile file, RegisterBO bo);

    /**
     * 登录业务
     * @param bo
     * @return
     */
    LoginVO login(LoginBO bo);

    /**
     * 获取短信验证码
     * @param bo
     */
    void getVerifyCode(VerifyCodeBO bo);

    /**
     * 校验token是否有效
     * @param token
     * @return
     */
    User checkToken(String token);

    /**
     * 修改密码
     * @param bo
     */
    void updatePwd(UpdatePwdBO bo);

    /**
     * 找回密码
     * @param bo
     */
    void forgotPwd(ForgotPwdBO bo);

    /**
     * 退出登录
     * @param token
     */
    void logout(String token);

    /**
     * 修改用户信息
     * @param file
     * @param bo
     */
    void updateUserInfo(MultipartFile file, UserInfoBO bo);

    /**
     * 校验原手机号
     * @param bo
     */
    void verifyOldMobile(UpdateMobileBO bo);

    /**
     * 绑定新手机号
     * @param bo
     */
    void bindNewMobile(UpdateMobileBO bo);

}
