package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 15:25
 * @description: 下单接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class OrderBO extends BaseBO {

    private static final long serialVersionUID = 7569023099860452795L;

    /**
     * 商品编号
     */
    private Integer productId;

    /**
     * 收货地址编号
     */
    private Integer addressId;

}
