package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author duanhaohao
 */
@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`comment_reply`")
public class CommentReply {
    /**
     * 评论id 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品编号
     */
    @Column(name = "`product_id`")
    private Integer productId;

    /**
     * 评论内容
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 评论/回复用户id
     */
    @Column(name = "`from_user_id`")
    private String fromUserId;

    /**
     * 评论/回复用户昵称
     */
    @Column(name = "`from_user_name`")
    private String fromUserName;

    /**
     * 评论/回复用户头像
     */
    @Column(name = "`from_user_avatar`")
    private String fromUserAvatar;

    /**
     * 评论/回复时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 类型 1-评论 2-回复
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 回复人编号
     */
    @Column(name = "`to_user_id`")
    private String toUserId;

    /**
     * 回复人昵称
     */
    @Column(name = "`to_user_name`")
    private String toUserName;

    /**
     * 回复人头像
     */
    @Column(name = "`to_user_avatar`")
    private String toUserAvatar;

    /**
     * 回复id
     */
    @Column(name = "`reply_id`")
    private Integer replyId;

}
