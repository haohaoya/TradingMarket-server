package top.hhduan.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "`chat_detail`")
public class ChatDetail {
    /**
     * 消息详情id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    /**
     * 消息对话框编号
     */
    @Column(name = "`chat_id`")
    @JsonIgnore
    private String chatId;

    /**
     * 消息f发送者用户编号
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 消息f发送者用户昵称
     */
    @Column(name = "`user_name`")
    private String userName;

    /**
     * 消息f发送者用户头像
     */
    @Column(name = "`user_avatar`")
    private String userAvatar;

    /**
     * 发送内容
     */
    @Column(name = "`content`")
    private String content;

    /**
     * 消息类型 1-用户消息 2-系统消息
     */
    @Column(name = "`type`")
    @JsonIgnore
    private Integer type;

    /**
     * 是否最后一条消息 1-是 2-否
     */
    @Column(name = "`is_latest`")
    private Integer isLatest;

    /**
     * 创建时间（发送时间）
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    @JsonIgnore
    private Date updateTime;

}
