package top.hhduan.market.service;

import top.hhduan.market.bo.PraiseBO;
import top.hhduan.market.entity.Praise;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/15 15:57
 * @description: 点赞相关接口
 * @version: 1.0
 */
public interface PraiseService extends BaseService<Praise, Integer> {

    /**
     * 点赞/取消点赞
     * @param bo
     */
    void praiseOrUnPraise(PraiseBO bo);

    /**
     * 获取点赞列表
     * @param bo
     * @return
     */
    Map<String, Object> getPraiseList(PraiseBO bo);

}
