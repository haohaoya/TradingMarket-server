package top.hhduan.market.mapper;

import top.hhduan.market.entity.Product;
import top.hhduan.market.bo.ProductSearchBO;
import top.hhduan.market.vo.ProductDetailsVO;
import top.hhduan.market.vo.ProductNumVO;
import top.hhduan.market.vo.ProductVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProductMapper extends Mapper<Product> {

    /**
     * 查询商品详情
     * @param productId
     * @return
     */
    ProductDetailsVO selectProductDetail(@Param("productId") Integer productId);

    /**
     * 查询首页商品列表
     * @param bo
     * @return
     */
    List<ProductVO> selectProductList(ProductSearchBO bo);

    /**
     * 个人中心--商品查询
     * @param type
     * @param userId
     * @return
     */
    List<ProductVO> selectMyProductList(@Param("type") Integer type, @Param("userId") String userId);

    /**
     * 个人中心--查询各类商品数量
     * @param userId
     * @return
     */
    ProductNumVO selectProductNum(@Param("userId") String userId);


    /**
     * 查询商品信息及交易状态
     * @param productId
     * @return
     */
    ProductVO selectProductInfoAndTradeStatus(Integer productId);
}
