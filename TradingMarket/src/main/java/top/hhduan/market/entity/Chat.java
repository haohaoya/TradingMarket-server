package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`chat`")
public class Chat {
    /**
     * 聊天主表id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 对方用户id
     */
    @Column(name = "`another_user_id`")
    private String anotherUserId;

    /**
     * 商品编号
     */
    @Column(name = "`product_id`")
    private Integer productId;

}
