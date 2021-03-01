package top.hhduan.market.controller;

import org.springframework.web.bind.annotation.RequestBody;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.bo.ProductSearchBO;
import top.hhduan.market.service.BannerService;
import top.hhduan.market.service.ProductService;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/8 17:08
 * @description: banner轮播图
 * @version: 1.0
 */
@RestController
@Slf4j
public class BannerController extends BaseController {

    @Autowired
    private BannerService bannerService;


    /**
     * 获取首页轮播图列表
     * @return
     */
    @RequestMapping(value = "getBannerList", method = RequestMethod.GET)
    public ResponseVO getBannerList() {
        return ResponseVO.success(bannerService.getBannerList());
    }
}
