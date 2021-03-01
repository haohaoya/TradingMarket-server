package top.hhduan.market.service;

import top.hhduan.market.bo.CommentReplyBO;
import top.hhduan.market.entity.CommentReply;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/17 13:28
 * @description: 评论/回复接口
 * @version: 1.0
 */
public interface CommentRelyService extends BaseService<CommentReply, Integer> {

    /**
     * 评论/回复
     * @param bo
     */
    void commentOrReply(CommentReplyBO bo);

}
