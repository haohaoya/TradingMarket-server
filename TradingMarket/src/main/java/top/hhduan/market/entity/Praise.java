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
@Table(name = "`praise`")
public class Praise {
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
     * 点赞用户编号
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 昵称
     */
    @Column(name = "`user_name`")
    private String userName;

    /**
     * 头像
     */
    @Column(name = "`user_avatar`")
    private String userAvatar;

    /**
     * 点赞状态 1-点赞 2-取消赞
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 点赞时间/取消点赞时间
     */
    @Column(name = "`praise_time`")
    private Date praiseTime;

}
