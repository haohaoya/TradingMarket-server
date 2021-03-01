package top.hhduan.market.controller;

import top.hhduan.market.bo.CommonOrderBO;
import top.hhduan.market.bo.OrderBO;
//import top.duanhaohao.market.rabbitmq.producer.DirectMqSender;
import top.hhduan.market.service.OrderService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/9/15 15:33
 * @description: 订单相关请求
 * @version: 1.0
 */
@RestController
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    /**
     * 下单--购买商品
     * @param bo
     * @return
     */
    @RequestMapping(value = "placeOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO placeOrder(@RequestBody OrderBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getProductId(), "商品编号不能为空");
        Assertion.isPositive(bo.getAddressId(), "收货信息不能为空");
        bo.setToken(token);
        return ResponseVO.success(orderService.placeOrder(bo));
    }

    /**
     * 订单详情信息
     * @param bo
     * @return
     */
    @RequestMapping(value = "getOrderDetails", method = RequestMethod.GET)
    public ResponseVO getOrderDetails(CommonOrderBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getOrderId(), "订单编号不能为空");
        bo.setToken(token);
        return ResponseVO.success(orderService.getOrderDetail(bo));
    }

    /**
     * 取消订单
     * @param bo
     * @return
     */
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public ResponseVO cancelOrder(@RequestBody CommonOrderBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getOrderId(), "订单编号不能为空");
        bo.setToken(token);
        orderService.cancelOrder(bo);
        return ResponseVO.success();
    }

/*
    @Autowired
    private DirectMqSender directMqSender;
*/
    /**
     * 测试rabbitmq
     */
/*
    @RequestMapping(value = "testorder", method = RequestMethod.GET)
    public void test() {
        directMqSender.sendTtlMessage(RabbitMqEnum.RoutingEnum.SECONDARY_ORDER_DEAD_LETTER_ROUTING, "123");
    }
*/

}
