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
@Table(name = "`banner`")
public class Banner {
    /**
     * 自增主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题/说明
     */
    @Column(name = "`title`")
    private String title;

    /**
     * 图片
     */
    @Column(name = "`image`")
    private String image;

    /**
     * 跳转URL链接
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 序号
     */
    @Column(name = "`serial_num`")
    private Integer serialNum;

    /**
     * 状态 1-有效 2-无效
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
    @Column(name = "`modify_time`")
    @JsonIgnore
    private Date modifyTime;

    /**
     * 修改人
     */
    @Column(name = "`modify_man`")
    @JsonIgnore
    private String modifyMan;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    @JsonIgnore
    private String remark;

}
