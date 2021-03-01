package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 22:22
 * @description: 修改密码接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UpdatePwdBO extends BaseBO {
    private static final long serialVersionUID = 1843708137559454805L;

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

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
