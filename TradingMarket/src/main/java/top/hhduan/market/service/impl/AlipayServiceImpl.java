package top.hhduan.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import top.hhduan.market.bo.AlipayBO;
import top.hhduan.market.config.AlipayConfig;
import top.hhduan.market.constants.AlipayConstants;
import top.hhduan.market.entity.Order;
import top.hhduan.market.entity.PaymentLog;
import top.hhduan.market.entity.PaymentNotifyLog;
import top.hhduan.market.entity.User;
import top.hhduan.market.mapper.OrderMapper;
import top.hhduan.market.mapper.PaymentLogMapper;
import top.hhduan.market.mapper.PaymentNotifyLogMapper;
import top.hhduan.market.service.AlipayService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/9/16 20:48
 * @description:
 * @version: 1.0
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentLogMapper paymentLogMapper;

    @Autowired
    private PaymentNotifyLogMapper paymentNotifyLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String alipay(AlipayBO bo) {
        User user = userService.checkToken(bo.getToken());
        // 查询订单状态
        Order order = orderMapper.selectByPrimaryKey(bo.getOrderId());
        Assertion.notNull(order, "订单不存在");
        Assertion.isTrue(1 == order.getTradeStatus(), "订单已取消或已结算");
        Assertion.isTrue(1 == order.getPayStatus() || 4 == order.getPayStatus(), "订单正在付款中或已付款");
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = System.currentTimeMillis() + "";
        // 订单名称，必填
        String subject = "手机网站支付测试商品";
        // 付款金额，必填
        String totalAmount = "0.01";
        // 商品描述，可空
        String body = "购买测试商品0.01元";
        // 超时时间 可空
        String timeoutExpress = "2m";
        // 销售产品码 必填
        String productCode = "QUICK_WAP_WAY";
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setBody(body);
        model.setTimeoutExpress(timeoutExpress);
        model.setProductCode(productCode);
        alipayRequest.setBizModel(model);
        // 设置后端异步通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        // 设置前端同步返回地址
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        log.info("alipayRequest: {}", JSON.toJSONString(alipayRequest));
        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipayRequest).getBody();
            log.info("form: {}", form);
        } catch (AlipayApiException e) {
            log.error("调用支付宝支付接口异常", e);
        }
        // 修改订单支付状态及收货信息
        Order update = new Order();
        Date date = new Date();
        update.setOrderId(order.getOrderId());
        update.setUpdateTime(date);
        update.setPayTime(date);
        update.setPayStatus(2);
        orderMapper.updateByPrimaryKeySelective(update);
        // 生成支付流水
        PaymentLog paymentLog = this.buildPaymentLog(alipayRequest, outTradeNo, totalAmount, user.getUserId(), bo.getOrderId());
        paymentLogMapper.insertSelective(paymentLog);
        return form;
    }


    @Override
    public String alipayTest(AlipayBO bo) {
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String outTradeNo = System.currentTimeMillis() + "";
        // 订单名称，必填
        String subject = "手机网站支付测试商品";
        // 付款金额，必填
        String totalAmount = "0.01";
        // 商品描述，可空
        String body = "购买测试商品0.01元";
        // 超时时间 可空
        String timeoutExpress = "2m";
        // 销售产品码 必填
        String productCode = "QUICK_WAP_WAY";
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setBody(body);
        model.setTimeoutExpress(timeoutExpress);
        model.setProductCode(productCode);
        alipayRequest.setBizModel(model);
        // 设置异步通知地址
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);
        // 设置同步地址 TODO 待前端提供地址
//        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        log.info("alipayRequest: {}", JSON.toJSONString(alipayRequest));
        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipayRequest).getBody();
            log.info("form: {}", form);
        } catch (AlipayApiException e) {
            log.error("调用支付宝支付接口异常", e);
        }
        PaymentLog paymentLog = this.buildPaymentLog(alipayRequest, outTradeNo, totalAmount, "", 0);
        paymentLogMapper.insertSelective(paymentLog);
        return form;
    }

    /**
     * 组装支付流水对象
     * @param alipayRequest
     * @param outTradeNo
     * @param totalAmount
     * @param userId
     * @param orderId
     * @return
     */
    private PaymentLog buildPaymentLog(AlipayTradeWapPayRequest alipayRequest, String outTradeNo, String totalAmount, String userId, Integer orderId) {
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setOrderId(orderId);
        paymentLog.setPayChannel(1);
        paymentLog.setRequestParams(JSON.toJSONString(alipayRequest));
        paymentLog.setRequestTime(new Date());
        paymentLog.setTradeNo(outTradeNo);
        paymentLog.setTradeAmount(new BigDecimal(totalAmount));
        paymentLog.setUserId(userId);
        return paymentLog;
    }

    @Override
    public String alipayNotify(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>(16);
        Map requestParams = request.getParameterMap();
        try {
            for (Object o : requestParams.keySet()) {
                String name = (String) o;
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk")
                params.put(name, valueStr);
            }
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 记录回调流水
            try {
                PaymentNotifyLog notifyLog = this.buildPaymentNotifyLog(outTradeNo, tradeNo, tradeStatus, JSON.toJSONString(params));
                paymentNotifyLogMapper.insertSelective(notifyLog);
            } catch (Exception e) {
                log.warn("支付宝回调流水记录异常", e);
            }
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            //计算得出通知验证结果
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            //验证成功
            if (verifyResult) {
                log.info("验签通过");
                //请在这里加上商户的业务逻辑程序代码
                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (AlipayConstants.TRADE_FINISHED.equals(tradeStatus)
                    || AlipayConstants.TRADE_SUCCESS.equals(tradeStatus)) {
                    try {
                        // 修改订单表、支付流水状态
                        this.updateOrderAndPaymentLogStatus(outTradeNo, params, tradeNo);
                    } catch (Exception e) {
                        log.error("支付回调状态修改异常", e);
                    }
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序
                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                    // TRADE_SUCCESS 交易成功：即时到账高级版。 交易成功，但卖家可以执行退款操作进行退款，即该交易还没有彻底完成，卖家还可以修改这笔交易。所以支付宝后续还会有至少一条推送（TRADE_FINISHED）
                    // TRADE_FINISHED 交易完成：即时到账普通版。 普通版不支持支付完成后的退款操作
                }
                return "success";
            } else {
                log.info("验签失败");
            }
        } catch (Exception e) {
            log.error("支付宝notify异常", e);
        }
        return "fail";
    }


    /**
     * 组装回调通知流水对象
     * @param outTradeNo
     * @param tradeNo
     * @param tradeStatus
     * @param params
     * @return
     */
    private PaymentNotifyLog buildPaymentNotifyLog(String outTradeNo, String tradeNo, String tradeStatus, String params) {
        PaymentNotifyLog notifyLog = new PaymentNotifyLog();
        notifyLog.setOutTradeNo(outTradeNo);
        notifyLog.setRequestParams(params);
        notifyLog.setTradeNo(tradeNo);
        notifyLog.setTradeStatus(tradeStatus);
        return notifyLog;
    }

    /**
     * 修改支付流水状态
     * @param outTradeNo
     * @param params
     * @param tradeNo
     */
    private void updateOrderAndPaymentLogStatus(String outTradeNo, Map<String, String> params, String tradeNo) {
        log.info("开始修改订单、支付流水记录");
        Example example = new Example(PaymentLog.class);
        example.createCriteria().andEqualTo("tradeNo", outTradeNo);
        PaymentLog paymentLog = paymentLogMapper.selectOneByExample(example);
        if (paymentLog == null) {
            throw new RuntimeException("支付流水不存在");
        }
        PaymentLog update = new PaymentLog();
        update.setOutTradeNo(tradeNo);
        update.setTradeStatus(params.get("trade_status"));
        update.setActualPayAmount(new BigDecimal(params.get("buyer_pay_amount")));
        update.setCompletionTime(DateUtils.parseStrToDate(params.get("gmt_payment")));
        update.setId(paymentLog.getId());
        paymentLogMapper.updateByPrimaryKeySelective(update);
        log.info("支付流水记录修改完成");
        Order order = orderMapper.selectByPrimaryKey(paymentLog.getOrderId());
        if (null == order) {
            throw new RuntimeException("订单信息不存在");
        }
       /* if (!order.getOrderAmount().equals(new BigDecimal(params.get("total_amount")))) {
            throw new RuntimeException("订单金额不一致");
        }*/
        Order entity = new Order();
        entity.setPayStatus(3);
        entity.setTradeStatus(3);
        entity.setOutTradeNo(tradeNo);
        entity.setPayAmount(new BigDecimal(params.get("buyer_pay_amount")));
        entity.setCompletionTime(DateUtils.parseStrToDate(params.get("gmt_payment")));
        entity.setOrderId(order.getOrderId());
        orderMapper.updateByPrimaryKeySelective(entity);
        log.info("订单记录修改完成");
    }

}
