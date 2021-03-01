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
@Table(name = "`payment_log`")
public class PaymentLog {
    /**
     * 自增主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品 订单号
     */
    @Column(name = "`order_id`")
    private Integer orderId;

    /**
     * 支付请求订单号
     */
    @Column(name = "`trade_no`")
    private String tradeNo;

    @Column(name = "`out_trade_no`")
    private String outTradeNo;

    /**
     * 订单交易状态
     */
    @Column(name = "`trade_status`")
    private String tradeStatus;

    /**
     * 交易金额
     */
    @Column(name = "`trade_amount`")
    private BigDecimal tradeAmount;

    /**
     * 实际支付金额
     */
    @Column(name = "`actual_pay_amount`")
    private BigDecimal actualPayAmount;

    /**
     * 用户编号
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 支付渠道 1-支付宝 2-微信
     */
    @Column(name = "`pay_channel`")
    private Integer payChannel;

    /**
     * 支付请求时间
     */
    @Column(name = "`request_time`")
    private Date requestTime;

    /**
     * 实际完成时间
     */
    @Column(name = "`completion_time`")
    private Date completionTime;

    @Column(name = "`request_params`")
    private String requestParams;

}
