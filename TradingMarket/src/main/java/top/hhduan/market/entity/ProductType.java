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
@Table(name = "`product_type`")
public class ProductType {
    /**
     * 类别id
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类别名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 状态 1-有效；2-无效
     */
    @Column(name = "`status`")
    @JsonIgnore
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    @JsonIgnore
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "`create_man`")
    @JsonIgnore
    private String createMan;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改人
     */
    @Column(name = "`update_man`")
    @JsonIgnore
    private String updateMan;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    @JsonIgnore
    private String remark;

}
