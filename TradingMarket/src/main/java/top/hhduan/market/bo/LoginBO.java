package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 10:41
 * @description: 登录接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class LoginBO extends BaseBO {


    private static final long serialVersionUID = -3644422290879832378L;
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

}
