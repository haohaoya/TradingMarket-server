package top.hhduan.market.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.entity.Address;
import top.hhduan.market.entity.Banner;
import top.hhduan.market.mapper.BannerMapper;
import top.hhduan.market.service.BannerService;
import org.springframework.stereotype.Service;
import top.hhduan.market.vo.ProductVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/8 17:01
 * @description:
 * @version: 1.0
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner, Integer> implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public List<Banner> getBannerList() {
        Example example = new Example(Banner.class);
        example.createCriteria().andEqualTo("status",1);
        example.setOrderByClause("serial_num asc, create_time desc");
        return bannerMapper.selectByExample(example);
    }
}
