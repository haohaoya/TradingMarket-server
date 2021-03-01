package top.hhduan.market.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/20 21:16
 * @description: 下单成功后返回信息
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class OrderVO implements Serializable {
    private static final long serialVersionUID = 1260713368186600984L;

    /**
     * 订单编号
     */
    private Integer orderId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 交易状态
     */
    private Integer tradeStatus;

    /**
     * 买家昵称
     */
    private String buyerUserName;

    /**
     *  下单时间
     */
    private Date createTime;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品图片
     */
    private String productImgs;

    /**
     * 收货人手机号
     */
    private String consigneeMobile;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人地址
     */
    private String consigneeAddress;

    /**
     * 订单剩余有效时间 秒
     */
    private Long expiredTime;

}
