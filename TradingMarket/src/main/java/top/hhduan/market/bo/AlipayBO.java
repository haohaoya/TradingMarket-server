package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 20:36
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class AlipayBO extends BaseBO {

    private static final long serialVersionUID = 761808750973621005L;
    /**
     * 订单号
     */
    private Integer orderId;

}
