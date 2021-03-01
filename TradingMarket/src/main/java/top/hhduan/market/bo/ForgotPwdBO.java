package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 23:55
 * @description: 找回密码接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ForgotPwdBO extends BaseBO {
    private static final long serialVersionUID = 6130444602859371457L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 短信验证码
     */
    @NotBlank(message = "短信验证码不能为空")
    private String verifyCode;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPwd;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPwd;

}
