package top.hhduan.market.controller;

import top.hhduan.market.entity.ProductType;
import top.hhduan.market.service.ProductTypeService;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/8 17:03
 * @description: 商品类别相关
 * @version: 1.0
 */
@RestController
@Slf4j
public class ProductTypeController extends BaseController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 获取商品类别列表
     * @return
     */
    @RequestMapping(value = "getProductTypeList", method = RequestMethod.GET)
    public ResponseVO getProductTypeList() {
        Example example = new Example(ProductType.class);
        Example.Criteria criteria = example.createCriteria();
        // 查询有效数据
        criteria.andEqualTo("status", 1);
        return ResponseVO.success(productTypeService.findList(example));
    }

}
