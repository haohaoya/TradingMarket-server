package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 13:32
 * @description: 评论/回复入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class CommentReplyBO extends BaseBO {

    private static final long serialVersionUID = 6388061367719863036L;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空")
    private Integer productId;

    /**
     * 评论/回复内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 回复目标用户 传参则表示回复 不传则表示评论
     */
    private String toUserId;

    /**
     * 回复id
     */
    private Integer replyId;

}
