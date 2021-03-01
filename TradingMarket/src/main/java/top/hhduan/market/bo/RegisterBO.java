package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 14:28
 * @description: 注册接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class RegisterBO extends BaseBO {

    private static final long serialVersionUID = 437006406778524416L;

    /** 昵称 */
    @NotBlank(message = "昵称不能为空")
    private String userName;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /** 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    /** 密码 */
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 确认密码 */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPwd;

}
