package top.hhduan.market.service.impl;

import top.hhduan.market.bo.CommonOrderBO;
import top.hhduan.market.bo.OrderBO;
import top.hhduan.market.constants.RedisConstants;
import top.hhduan.market.entity.Address;
import top.hhduan.market.entity.Order;
import top.hhduan.market.entity.User;
import top.hhduan.market.enums.RabbitMqEnum;
import top.hhduan.market.mapper.AddressMapper;
import top.hhduan.market.mapper.OrderMapper;
import top.hhduan.market.mapper.ProductMapper;
//import top.hhduan.market.rabbitmq.producer.DirectMqSender;
import top.hhduan.market.service.OrderService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.utils.RedisUtils;
import top.hhduan.market.vo.OrderVO;
import top.hhduan.market.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/9/15 15:23
 * @description:
 * @version: 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl<Order, Integer> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductMapper productMapper;

 //  @Autowired
 //   private DirectMqSender directMqSender;

    @Autowired
    private AddressMapper addressMapper;

    @Value("${spring.rabbitmq.order.ttl}")
    private int ttl;

    @Override
    public OrderVO placeOrder(OrderBO bo) {
        // 校验token
        User user = userService.checkToken(bo.getToken());
        // 校验商品是否存在
        ProductVO productVO = productMapper.selectProductInfoAndTradeStatus(bo.getProductId());
        Assertion.notNull(productVO, "商品信息不存在");
        Assertion.isTrue(2 == productVO.getTradeStatus(), "该商品正在交易中或已卖出");
        Address address = addressMapper.selectByPrimaryKey(bo.getAddressId());
        Assertion.notNull(address, "收货信息不存在");
        Assertion.isTrue(1 == address.getStatus(), "收货信息已被删除");
        Date date = new Date();
        // 生成待支付订单
        Order order = new Order();
        order.setBuyingUserId(user.getUserId());
        order.setCreateTime(date);
        order.setOrderAmount(productVO.getProductPrice());
        order.setProductId(productVO.getId());
        order.setUpdateTime(date);
        String consigneeAddress = address.getProvince() + " " + address.getCity() + address.getDistrict() + address.getStreet()
                + " " + address.getAddressDetail();
        order.setConsigneeAddress(consigneeAddress);
        order.setConsigneeMobile(address.getConsigneeMobile());
        order.setConsigneeName(address.getConsigneeName());
        orderMapper.insertSelective(order);
        int orderId = order.getOrderId();
        // 发送延时队列
//        directMqSender.sendTtlMessage(RabbitMqEnum.RoutingEnum.SECONDARY_ORDER_DEAD_LETTER_ROUTING, orderId + "");
        // 订单有效时长写入redis
        String key = String.format(RedisConstants.ORDER_ID, orderId);
        RedisUtils.setEx(key, ttl / 1000, orderId + "");
        // 组装返回信息
        OrderVO vo = new OrderVO();
        vo.setOrderId(orderId);
        vo.setCreateTime(date);
        vo.setOrderAmount(order.getOrderAmount());
        vo.setPayStatus(1);
        vo.setTradeStatus(1);
        vo.setProductDesc(productVO.getProductDesc());
        vo.setProductImgs(productVO.getProductImgs());
        vo.setBuyerUserName(user.getUserName());
        vo.setExpiredTime((long) ttl);
        return vo;
    }

    @Override
    public void cancelOrder(CommonOrderBO bo) {
        // 校验token
        User user = userService.checkToken(bo.getToken());
        // 校验订单是否存在
        Order order = orderMapper.selectByPrimaryKey(bo.getOrderId());
        Assertion.notNull(order, "订单不存在");
        Assertion.isTrue(1 == order.getTradeStatus(), "订单已取消或已结算，请勿重复操作");
        Assertion.isTrue(1 == order.getPayStatus() || 4 == order.getPayStatus(), "订单正在付款中或已付款，无法取消");
        Order update = new Order();
        update.setOrderId(bo.getOrderId());
        update.setTradeStatus(2);
        update.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public OrderVO getOrderDetail(CommonOrderBO bo) {
        // 校验token
        User user = userService.checkToken(bo.getToken());
        OrderVO vo = orderMapper.selectOrderDetailByOrderId(bo.getOrderId());
        Assertion.notNull(vo, "订单信息不存在");
        String key = String.format(RedisConstants.ORDER_ID, bo.getOrderId());
        long expiredTime = RedisUtils.pttl(key);
        log.info("expiredTime:" + expiredTime);
        vo.setExpiredTime(Detect.isPositive(expiredTime) ? expiredTime / 1000 : 0);
        return vo;
    }
}
