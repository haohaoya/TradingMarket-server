package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:19
 * @description: 修改手机号入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UpdateMobileBO extends BaseBO {
    private static final long serialVersionUID = 4656015852611448064L;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String verifyCode;

}
