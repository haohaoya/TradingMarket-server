package top.hhduan.market.service.impl;

import top.hhduan.market.bo.PraiseBO;
import top.hhduan.market.entity.Praise;
import top.hhduan.market.entity.Product;
import top.hhduan.market.entity.User;
import top.hhduan.market.mapper.PraiseMapper;
import top.hhduan.market.mapper.ProductMapper;
import top.hhduan.market.service.PraiseService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/15 15:58
 * @description: 点赞相关服务
 * @version: 1.0
 */
@Service
@Slf4j
public class PraiseServiceImpl extends BaseServiceImpl<Praise, Integer> implements PraiseService {

    @Autowired
    private PraiseMapper praiseMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void praiseOrUnPraise(PraiseBO bo) {
        User user = userService.checkToken(bo.getToken());

        // 查询商品是否存在
        Product product = productMapper.selectByPrimaryKey(bo.getProductId());
        Assertion.notNull(product, "商品不存在");
        // 查询是否存在点赞记录
        Example example = new Example(Praise.class);
        example.createCriteria()
                .andEqualTo("userId", user.getUserId())
                .andEqualTo("productId", bo.getProductId());
        List<Praise> list = praiseMapper.selectByExample(example);
        Date date = new Date();
        if (Detect.notEmpty(list)) {
            Praise praise = Detect.firstOne(list);
            // 已存在点赞记录
            if (null != praise) {
                // 重复点赞或重复取消赞
                Assertion.notEquals(praise.getStatus(), bo.getStatus(), "请勿重复操作");
                BeanUtils.copyProperties(bo, praise);
                praise.setPraiseTime(date);
                praise.setUserAvatar(user.getUserAvatar());
                praise.setUserName(user.getUserName());
                praiseMapper.updateByPrimaryKeySelective(praise);
                return;
            }
        }
        Assertion.isTrue(bo.getStatus() == 1, "点赞状态异常");
        // 不存在点赞记录
        Praise praise = new Praise();
        praise.setProductId(bo.getProductId());
        praise.setUserId(user.getUserId());
        praise.setPraiseTime(date);
        praise.setUserAvatar(user.getUserAvatar());
        praise.setUserName(user.getUserName());
        praiseMapper.insertSelective(praise);
    }

    @Override
    public Map<String, Object> getPraiseList(PraiseBO bo) {
        Example example = new Example(Praise.class);
        example.createCriteria().andEqualTo("productId", bo.getProductId())
                .andEqualTo("status", 1)
        ;
        example.orderBy("praiseTime");
        Map<String, Object> map = new HashMap<>(16);
        map.put("list", praiseMapper.selectByExample(example));
        if (!Detect.notEmpty(bo.getToken())) {
            map.put("praiseStatus", 2);
            return map;
        }
        // token不为空（已登录） 校验有效性
        User user = userService.checkToken(bo.getToken());
        example.clear();
        example.createCriteria().andEqualTo("productId", bo.getProductId())
                .andEqualTo("userId", user.getUserId());
        List<Praise> list = praiseMapper.selectByExample(example);
        if (!Detect.notEmpty(list)) {
            map.put("praiseStatus", 2);
            return map;
        }
        Praise praise = Detect.firstOne(list);
        if (null == praise) {
            map.put("praiseStatus", 2);
            return map;
        }
        map.put("praiseStatus", praise.getStatus());
        return map;
    }
}
