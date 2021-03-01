package top.hhduan.market.service;

import top.hhduan.market.bo.CommonOrderBO;
import top.hhduan.market.bo.OrderBO;
import top.hhduan.market.entity.Order;
import top.hhduan.market.vo.OrderVO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/9/15 15:22
 * @description: 订单相关接口
 * @version: 1.0
 */
public interface OrderService extends BaseService<Order, Integer> {

    /**
     * 下单--购买商品
     * @param bo
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    OrderVO placeOrder(OrderBO bo);

    /**
     * 取消订单
     * @param bo
     */
    void cancelOrder(CommonOrderBO bo);

    /**
     * 订单详情信息
     * @param bo
     * @return
     */
    OrderVO getOrderDetail(CommonOrderBO bo);
}
