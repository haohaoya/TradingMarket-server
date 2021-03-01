package top.hhduan.market.mapper;

import top.hhduan.market.entity.Order;
import top.hhduan.market.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {

    /**
     * 订单详情页信息查询
     * @param orderId
     * @return
     */
    OrderVO selectOrderDetailByOrderId(@Param("orderId") Integer orderId);
}
