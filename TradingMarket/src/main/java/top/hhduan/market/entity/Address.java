package top.hhduan.market.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`address`")
public class Address {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户编号
     */
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 收货人姓名
     */
    @Column(name = "`consignee_name`")
    private String consigneeName;

    /**
     * 收货人手机号
     */
    @Column(name = "`consignee_mobile`")
    private String consigneeMobile;

    /**
     * 省
     */
    @Column(name = "`province`")
    private String province;

    /**
     * 市
     */
    @Column(name = "`city`")
    private String city;

    /**
     * 区
     */
    @Column(name = "`district`")
    private String district;

    /**
     * 街道
     */
    @Column(name = "`street`")
    private String street;

    /**
     * 详细地址
     */
    @Column(name = "`address_detail`")
    private String addressDetail;

    /**
     * 是否为默认地址 1-默认 2-非默认
     */
    @Column(name = "`is_default_address`")
    private Integer isDefaultAddress;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    @JsonIgnore
    private Date updateTime;

    /**
     * 状态 1-有效 2-删除
     */
    @Column(name = "`status`")
    @JsonIgnore
    private Integer status;

}
