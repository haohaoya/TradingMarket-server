package top.hhduan.market.controller;

import com.github.pagehelper.PageHelper;
import top.hhduan.market.bo.*;
import top.hhduan.market.service.ProductService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.hhduan.market.bo.*;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/8 16:35
 * @description: 商品相关
 * @version: 1.0
 */
@RestController
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 获取商品列表 -- 不需要登录
     * @param bo
     *          商品名称，商品类别动态查询
     * @return
     */
    @RequestMapping(value = "getProductList", method = RequestMethod.POST)
    public ResponseVO getProductList(@RequestBody ProductSearchBO bo) {
        PageHelper.startPage(bo.getPageNum(), bo.getPageSize());
        return ResponseVO.successPageInfo(productService.getProductList(bo));

    }

    /**
     * 发布商品
     * @param productImgs
     * @param bo
     * @return
     */
    @RequestMapping(value = "publishProduct")
    public ResponseVO publishProduct(@RequestParam("productImgs") MultipartFile[] productImgs, @Valid PublishProductBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.notNull(productImgs, "请选择图片");
        bo.setToken(token);
        productService.publishProduct(productImgs, bo);
        return ResponseVO.success();
    }

    /**
     * 获取商品列表 -- 登录后查询
     * @param bo
     *          商品名称，商品类别动态查询
     * @return
     */
    @RequestMapping(value = "getMyProductList", method = RequestMethod.POST)
    public ResponseVO getMyProductList(@Valid @RequestBody MyProductSearchBO bo) {
        Assertion.isTrue(bo != null, "参数为空");
        // 校验token是否为空
        String token = checkToken();
        Assertion.isTrue(1 == bo.getType() || 2 == bo.getType() || 3 == bo.getType(), "type类型错误");
        bo.setToken(token);

        return ResponseVO.successPageInfo(productService.getMyProductList(bo));
    }

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    @RequestMapping(value = "getProductDetail", method = RequestMethod.GET)
    public ResponseVO getProductDetail(Integer productId) {
        Assertion.isPositive(productId, "商品编号不能为空");
        return ResponseVO.success(productService.getProductDetail(productId));
    }

    /**
     * 修改商品信息
     * @param productImgs
     * @param bo
     * @return
     */
    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    public ResponseVO updateProduct(@RequestParam("productImgs") MultipartFile[] productImgs, @Valid UpdateProductBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        productService.updateProduct(productImgs, bo);
        return ResponseVO.success();
    }

    /**
     * 查询各类商品数量
     * @return
     */
    @RequestMapping(value = "getProductNum", method = RequestMethod.GET)
    public ResponseVO getProductNum() {
        // 校验token是否为空
        String token = checkToken();
        return ResponseVO.success(productService.getProductNum(token));
    }

    /**
     * 删除我发布的商品
     * @param bo
     * @return
     */
    @RequestMapping(value = "delProduct", method = RequestMethod.POST)
    public ResponseVO delProduct(@RequestBody DelProductBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getProductId(), "商品编号不能为空");
        Assertion.isPositive(bo.getType(), "type不能为空");
        Assertion.isTrue(1 == bo.getType() || 2 == bo.getType() || 3 == bo.getType(), "type参数异常");
        bo.setToken(token);
        productService.delProduct(bo);
        return ResponseVO.success();
    }

}
