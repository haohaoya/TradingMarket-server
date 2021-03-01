package top.hhduan.market.controller;

import top.hhduan.market.bo.*;
import top.hhduan.market.constants.VerifyCodeConstants;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.hhduan.market.bo.*;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/7 14:25
 * @description: 用户相关
 * @version: 1.0
 */
@RestController
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     * @param bo
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO register(@RequestParam("userAvatar") MultipartFile userAvatar, @Valid RegisterBO bo) {
        Assertion.notNull(userAvatar, "请选择图片");
        Assertion.isMobile(bo.getMobile(), "请输入正确的手机号");
        Assertion.isTrue(Detect.equals(bo.getPassword(), bo.getConfirmPwd()), "两次密码输入不一致");
        userService.register(userAvatar, bo);
        return ResponseVO.success();
    }

    /**
     * 登录接口
     * @param bo
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO login(@Valid @RequestBody LoginBO bo) {
        return ResponseVO.success(userService.login(bo));
    }

    /**
     * 获取验证码接口
     * @param bo
     * @return
     */
    @RequestMapping(value = "getVerifyCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO getVerifyCode(@Valid @RequestBody VerifyCodeBO bo) {
        Assertion.isMobile(bo.getMobile(), "请输入正确的手机号");
        Assertion.isTrue(VerifyCodeConstants.REGISTER == bo.getType()
                || VerifyCodeConstants.RETRIEVE_PWD == bo.getType()
                || VerifyCodeConstants.OLD_MOBILE == bo.getType()
                || VerifyCodeConstants.NEW_MOBILE == bo.getType(), "短信类型错误");
        if (VerifyCodeConstants.OLD_MOBILE == bo.getType()) {
            // 校验token是否为空
            String token = checkToken();
            bo.setToken(token);
        }
        userService.getVerifyCode(bo);
        return ResponseVO.success();
    }

    /**
     * 修改密码
     * @param bo
     * @return
     */
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO updatePwd(@Valid @RequestBody UpdatePwdBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.equals(bo.getNewPwd(), bo.getConfirmPwd(), "两次密码输入不一致");
        bo.setToken(token);
        userService.updatePwd(bo);
        return ResponseVO.success();
    }

    /**
     * 忘记密码
     * @param bo
     * @return
     */
    @RequestMapping(value = "forgotPwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO forgotPwd(@Valid @RequestBody ForgotPwdBO bo) {
        Assertion.equals(bo.getNewPwd(), bo.getConfirmPwd(), "两次密码输入不一致");
        userService.forgotPwd(bo);
        return ResponseVO.success();
    }

    /**
     * 判断登录是否有效
     * @return
     */
    @RequestMapping(value = "checkLoginValid", method = RequestMethod.POST)
    public ResponseVO checkLoginValid() {
        // 校验token是否为空
        String token = checkToken();
        userService.checkToken(token);
        return ResponseVO.success();
    }

    /**
     * 修改用户信息
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public ResponseVO updateUserInfo(MultipartFile userAvatar, UserInfoBO bo) {
        // 校验token是否为空
        String token = checkToken();
        boolean flag = bo == null;
        Assertion.isTrue(!flag, "参数异常");
        bo.setToken(token);
        userService.updateUserInfo(userAvatar, bo);
        return ResponseVO.success();
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseVO logout() {
        // 校验token是否为空
        String token = checkToken();
        userService.logout(token);
        return ResponseVO.success();
    }

    /**
     * 校验旧手机号
     * @return
     */
    @RequestMapping(value = "verifyOldMobile", method = RequestMethod.POST)
    public ResponseVO verifyOldMobile(@RequestBody UpdateMobileBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.notEmpty(bo.getMobile(), "旧手机号不能为空");
        Assertion.notEmpty(bo.getVerifyCode(), "验证码不能为空");
        bo.setToken(token);
        userService.verifyOldMobile(bo);
        return ResponseVO.success();
    }

    /**
     * 绑定新手机号
     * @return
     */
    @RequestMapping(value = "bindNewMobile", method = RequestMethod.POST)
    public ResponseVO bindNewMobile(@RequestBody UpdateMobileBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.notEmpty(bo.getMobile(), "新手机号不能为空");
        Assertion.notEmpty(bo.getVerifyCode(), "验证码不能为空");
        bo.setToken(token);
        userService.bindNewMobile(bo);
        return ResponseVO.success();
    }
}
