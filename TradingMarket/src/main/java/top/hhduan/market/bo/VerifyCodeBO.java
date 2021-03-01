package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 14:07
 * @description: 发送短信验证码接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class VerifyCodeBO extends BaseBO {

    private static final long serialVersionUID = -8940747596809818155L;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /** 短信类型 1--注册验证码 2--找回密码验证码 */
    @NotNull(message = "短信类型不能为空")
    private Integer type;

}
