package top.hhduan.market.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/21 11:10
 * @description: Token信息
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class TokenVO implements Serializable {

    private static final long serialVersionUID = 1894308742642787496L;
    /**
     * 客户编号
     */
    private String userId;

    /**
     * 手机号
     */
    private String mobile;

}
