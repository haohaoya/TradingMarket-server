package top.hhduan.market.service.impl;

import top.hhduan.market.bo.CommentReplyBO;
import top.hhduan.market.entity.CommentReply;
import top.hhduan.market.entity.Product;
import top.hhduan.market.entity.User;
import top.hhduan.market.mapper.CommentReplyMapper;
import top.hhduan.market.mapper.ProductMapper;
import top.hhduan.market.service.CommentRelyService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/17 13:29
 * @description: 评论/回复相关业务
 * @version: 1.0
 */
@Service
@Slf4j
public class CommentReplyServiceImpl extends BaseServiceImpl<CommentReply, Integer> implements CommentRelyService {

    @Autowired
    private CommentReplyMapper commentReplyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void commentOrReply(CommentReplyBO bo) {
        User user = userService.checkToken(bo.getToken());
        // 查询商品是否存在
        Product product = productMapper.selectByPrimaryKey(bo.getProductId());
        Assertion.notNull(product, "商品不存在");
        CommentReply commentReply = new CommentReply();
        // 回复
        if (Detect.notEmpty(bo.getToUserId())) {
            Assertion.isPositive(bo.getReplyId(), "replyId不能为空");
            Example example = new Example(CommentReply.class);
            example.createCriteria().andEqualTo("productId", bo.getProductId())
                    .andEqualTo("fromUserId", bo.getToUserId());
            List<CommentReply> list = commentReplyMapper.selectByExample(example);
            Assertion.notEmpty(list, "回复异常，未找到相关评论/回复记录");
            User toUser = userService.findById(bo.getToUserId());
            commentReply.setToUserAvatar(toUser.getUserAvatar());
            commentReply.setToUserName(toUser.getUserName());
        } else {
            Assertion.isTrue(!Detect.isPositive(bo.getReplyId()), "参数异常：replyId");
        }
        BeanUtils.copyProperties(bo, commentReply);
        commentReply.setCreateTime(new Date());
        commentReply.setType(Detect.notEmpty(bo.getToUserId()) ? 2 : 1);
        commentReply.setFromUserId(user.getUserId());
        commentReply.setFromUserAvatar(user.getUserAvatar());
        commentReply.setFromUserName(user.getUserName());
        commentReplyMapper.insertSelective(commentReply);
    }
}
