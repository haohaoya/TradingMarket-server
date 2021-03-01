package top.hhduan.market.service.impl;

import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import top.hhduan.market.bo.*;
import top.hhduan.market.entity.Order;
import top.hhduan.market.entity.Product;
import top.hhduan.market.entity.User;
import top.hhduan.market.mapper.BannerMapper;
import top.hhduan.market.mapper.OrderMapper;
import top.hhduan.market.mapper.ProductMapper;
import top.hhduan.market.service.ProductService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.DateUtils;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.utils.OssUtil;
import top.hhduan.market.vo.ProductDetailsVO;
import top.hhduan.market.vo.ProductNumVO;
import top.hhduan.market.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/8 16:36
 * @description:
 * @version: 1.0
 */
@Service
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl<Product, Integer> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void publishProduct(MultipartFile[] files, PublishProductBO bo) {
        User user = userService.checkToken(bo.getToken());
        // oss存储
        OSSClient ossClient = OssUtil.getOssClient();
        String bucketName = OssUtil.getBucketName();
        String diskName = "images/product/" + DateUtils.getTimeString(new Date());
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = OssUtil.batchUploadInputStreamObject2Oss(ossClient, files, bucketName, diskName);
        } catch (Exception e) {
            Assertion.isTrue(true, "上传失败");
        }
        Assertion.notNull(stringBuilder, "文件上传失败");
        String productImgs = stringBuilder.substring(0, stringBuilder.length() - 1);
        Product product = new Product();
        Date date = new Date();
        BeanUtils.copyProperties(bo, product);
        product.setProductImgs(productImgs);
        product.setCreateTime(date);
        product.setUpdateTime(date);
        product.setPublishUserId(user.getUserId());
        productMapper.insertSelective(product);
    }

    @Override
    public List<ProductVO> getProductList(ProductSearchBO bo) {
        return productMapper.selectProductList(bo);
    }

    @Override
    public List<ProductVO> getMyProductList(MyProductSearchBO bo) {
        User user = userService.checkToken(bo.getToken());
        PageHelper.startPage(bo.getPageNum(), bo.getPageSize());
        return productMapper.selectMyProductList(bo.getType(), user.getUserId());
    }

    @Override
    public ProductDetailsVO getProductDetail(Integer productId) {
        return productMapper.selectProductDetail(productId);
    }

    @Override
    public void updateProduct(MultipartFile[] files, UpdateProductBO bo) {
        User user = userService.checkToken(bo.getToken());
        Product product = productMapper.selectByPrimaryKey(bo.getProductId());
        Assertion.notNull(product, "商品信息不存在");
        Assertion.equals(product.getPublishUserId(), user.getUserId(), "只有商品发布人可以修改");
        String productImgs = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (Detect.notEmpty(files)) {
            // oss存储
            OSSClient ossClient = OssUtil.getOssClient();
            String bucketName = OssUtil.getBucketName();
            String diskName = "images/product/" + DateUtils.getTimeString(new Date());
            try {
                stringBuilder = OssUtil.batchUploadInputStreamObject2Oss(ossClient, files, bucketName, diskName);
            } catch (Exception e) {
                Assertion.isTrue(true, "上传失败");
            }
            Assertion.notNull(stringBuilder, "文件上传失败");
        }
        if (Detect.notEmpty(bo.getOldImgs())) {
            for (String url : bo.getOldImgs()) {
                stringBuilder.append(url).append(",");
            }
        }
        if (stringBuilder.length() > 1) {
            productImgs = stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        Product update = new Product();
        Date date = new Date();
        BeanUtils.copyProperties(bo, update);
        update.setProductImgs(productImgs);
        update.setId(bo.getProductId());
        update.setUpdateTime(date);
        productMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public Map<String, Object> getProductNum(String token) {
        User user = userService.checkToken(token);
        ProductNumVO vo = productMapper.selectProductNum(user.getUserId());
        Map<String, Object> publish = new HashMap<>(16);
        Map<String, Object> purchase = new HashMap<>(16);
        Map<String, Object> sale = new HashMap<>(16);
        publish.put("num", vo.getPublishNum());
        publish.put("money", vo.getPublishAmount());
        purchase.put("num", vo.getPurchaseNum());
        purchase.put("money", vo.getPurchaseAmount());
        sale.put("num", vo.getSaleNum());
        sale.put("money", vo.getSaleAmount());
        Map<String, Object> result = new HashMap<>(16);
        result.put("publish", publish);
        result.put("purchase", purchase);
        result.put("sale", sale);
        return result;
    }


    @Override
    public void delProduct(DelProductBO bo) {
        User user = userService.checkToken(bo.getToken());
        ProductVO product = productMapper.selectProductInfoAndTradeStatus(bo.getProductId());
        Assertion.notNull(product, "商品不存在");
        if (1 == bo.getType()) {
            // 删除我发布的
            Assertion.equals(user.getUserId(), product.getPublishUserId(), "只能删除自己发布的商品");
            Assertion.equals(product.getTradeStatus(), 2, "该商品正在交易中，不能被删除");
            productMapper.deleteByPrimaryKey(bo.getProductId());
        } else if (2 == bo.getType()) {
            // 删除我卖出的
            Assertion.equals(product.getTradeStatus(), 3, "商品状态异常");
            Assertion.isPositive(product.getOrderId(), "商品状态异常");
            Order order = new Order();
            order.setSellingStatus(2);
            order.setUpdateTime(new Date());
            order.setOrderId(product.getOrderId());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            // 删除我买到的
            Assertion.equals(product.getTradeStatus(), 3, "商品状态异常");
            Assertion.isPositive(product.getOrderId(), "商品状态异常");
            Order order = new Order();
            order.setBuyingStatus(2);
            order.setUpdateTime(new Date());
            order.setOrderId(product.getOrderId());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }
}
