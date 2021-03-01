package top.hhduan.market.service;

import top.hhduan.market.bo.*;
import top.hhduan.market.entity.Product;
import top.hhduan.market.vo.ProductDetailsVO;
import top.hhduan.market.vo.ProductVO;
import org.springframework.web.multipart.MultipartFile;
import top.hhduan.market.bo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/8 16:36
 * @description:
 * @version: 1.0
 */
public interface ProductService extends BaseService<Product, Integer> {

    /**
     * 发布商品
     * @param files
     * @param bo
     */
    void publishProduct(MultipartFile[] files, PublishProductBO bo);

    /**
     * 首页商品列表查询
     * @param bo
     * @return
     */
    List<ProductVO> getProductList(ProductSearchBO bo);

    /**
     * 个人中心--查询我的商品列表
     * @param bo
     * @return
     */
    List<ProductVO> getMyProductList(MyProductSearchBO bo);

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    ProductDetailsVO getProductDetail(Integer productId);

    /**
     * 修改商品信息
     * @param files
     * @param bo
     */
    void updateProduct(MultipartFile[] files, UpdateProductBO bo);

    /**
     * 查询各类商品数量（我发布的、我购买的、我卖出的）
     * @param token
     * @return
     */
    Map<String, Object> getProductNum(String token);


    /**
     * 个人中心--商品列表删除
     * @param bo
     */
    void delProduct(DelProductBO bo);
}
