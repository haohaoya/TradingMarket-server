package top.hhduan.market.controller;

import top.hhduan.market.bo.CommentReplyBO;
import top.hhduan.market.entity.CommentReply;
import top.hhduan.market.service.CommentRelyService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/17 13:46
 * @description: 评论/回复相关
 * @version: 1.0
 */
@RestController
@Slf4j
public class CommentReplyController extends BaseController {

    @Autowired
    private CommentRelyService commentRelyService;

    /**
     * 评论/回复接口
     * @param bo
     * @return
     */
    @RequestMapping(value = "commentOrReply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO commentOrReply(@Valid @RequestBody CommentReplyBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        commentRelyService.commentOrReply(bo);
        return ResponseVO.success();
    }

    /**
     * 获取评论/回复列表
     * @param productId
     * @return
     */
    @RequestMapping(value = "getCommentReplyList", method = RequestMethod.GET)
    public ResponseVO getCommentReplyList(Integer productId) {
        Assertion.isPositive(productId, "商品编号不能为空");
        Example example = new Example(CommentReply.class);
        example.createCriteria().andEqualTo("productId", productId);
        example.orderBy("createTime");
        return ResponseVO.success(commentRelyService.findList(example));
    }


}
