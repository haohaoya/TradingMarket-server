package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`payment_notify_log`")
public class PaymentNotifyLog {
    /**
     * 自增主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 第三方支付订单号
     */
    @Column(name = "`trade_no`")
    private String tradeNo;

    /**
     * 系统订单号
     */
    @Column(name = "`out_trade_no`")
    private String outTradeNo;

    /**
     * 支付状态
     */
    @Column(name = "`trade_status`")
    private String tradeStatus;

    /**
     * 回调请求入参
     */
    @Column(name = "`request_params`")
    private String requestParams;

}
