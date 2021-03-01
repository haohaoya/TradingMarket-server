package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 8:46
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class CommonOrderBO extends BaseBO {

    private static final long serialVersionUID = -9078025283345783996L;
    /**
     * 订单编号
     */
    private Integer orderId;

}
