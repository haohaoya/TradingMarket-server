package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`order`")
public class Order {
    /**
     * 订单编号
     */
    @Id
    @Column(name = "`order_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    /**
     * 购买人id
     */
    @Column(name = "`buying_user_id`")
    private String buyingUserId;

    @Column(name = "`product_id`")
    private Integer productId;

    /**
     * 1-已下单 2-已取消 3-已结算
     */
    @Column(name = "`trade_status`")
    private Integer tradeStatus;

    /**
     * 1-待付款 2-付款中 3-已付款 4-付款失败
     */
    @Column(name = "`pay_status`")
    private Integer payStatus;

    /**
     * 购买状态 1-有效 2-删除
     */
    @Column(name = "`buying_status`")
    private Integer buyingStatus;

    /**
     * 卖出状态 1-有效 2-删除
     */
    @Column(name = "`selling_status`")
    private Integer sellingStatus;

    /**
     * 订单金额
     */
    @Column(name = "`order_amount`")
    private BigDecimal orderAmount;

    /**
     * 支付金额
     */
    @Column(name = "`pay_amount`")
    private BigDecimal payAmount;

    /**
     * 支付时间
     */
    @Column(name = "`pay_time`")
    private Date payTime;

    /**
     * 订单完成时间
     */
    @Column(name = "`completion_time`")
    private Date completionTime;

    /**
     * 第三方支付流水号
     */
    @Column(name = "`out_trade_no`")
    private String outTradeNo;

    /**
     * 收货人手机号
     */
    @Column(name = "`consignee_mobile`")
    private String consigneeMobile;

    /**
     * 收货人姓名
     */
    @Column(name = "`consignee_name`")
    private String consigneeName;

    /**
     * 收货地址
     */
    @Column(name = "`consignee_address`")
    private String consigneeAddress;

    /**
     * 订单创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 订单备注
     */
    @Column(name = "`remark`")
    private String remark;

}
