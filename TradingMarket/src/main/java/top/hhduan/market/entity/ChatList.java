package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`chat_list`")
public class ChatList {
    /**
     * 主表自增id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 聊天主表id
     */
    @Column(name = "`chat_id`")
    private String chatId;

    /**
     * 商品编号
     */
    @Column(name = "`product_id`")
    private Integer productId;

    /**
     * 当前用户编号
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 当前用户昵称
     */
    @Column(name = "`user_name`")
    private String userName;

    /**
     * 当前用户头像
     */
    @Column(name = "`user_avatar`")
    private String userAvatar;

    /**
     * 对方用户编号
     */
    @Column(name = "`another_user_id`")
    private String anotherUserId;

    /**
     * 对方用户昵称
     */
    @Column(name = "`another_user_name`")
    private String anotherUserName;

    /**
     * 对方用户头像
     */
    @Column(name = "`another_user_avatar`")
    private String anotherUserAvatar;

    /**
     * 是否在线 1-是 2-否
     */
    @Column(name = "`is_online`")
    private Integer isOnline;

    /**
     * 未读数
     */
    @Column(name = "`unread`")
    private Integer unread;

    /**
     * 状态 1-有效 2-删除
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

}
