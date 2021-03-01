package top.hhduan.market.service;

import top.hhduan.market.entity.Banner;
import top.hhduan.market.vo.ProductVO;
import top.hhduan.market.bo.BaseBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/8 17:01
 * @description:
 * @version: 1.0
 */
public interface BannerService extends BaseService<Banner, Integer> {

    /**
     * 获取banner列表
     * @return
     */
    List<Banner> getBannerList();
}
