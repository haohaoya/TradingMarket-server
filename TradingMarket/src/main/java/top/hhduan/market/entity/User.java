package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`user`")
public class User {
    /**
     * 用户编号
     */
    @Id
    @Column(name = "`user_id`")
    private String userId;

    /**
     * 手机号
     */
    @Column(name = "`mobile`")
    private String mobile;

    /**
     * 密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 昵称
     */
    @Column(name = "`user_name`")
    private String userName;

    /**
     * 签名
     */
    @Column(name = "`sign`")
    private String sign;

    /**
     * 来源 1——H5
     */
    @Column(name = "`source`")
    private Integer source;

    /**
     * 用户头像
     */
    @Column(name = "`user_avatar`")
    private String userAvatar;

    /**
     * 登录次数
     */
    @Column(name = "`login_counts`")
    private Integer loginCounts;

    /**
     * 最后登录时间
     */
    @Column(name = "`last_login_time`")
    private Date lastLoginTime;

    /**
     * 是否登录 1-已登录 2-未登录
     */
    @Column(name = "`login_status`")
    private Integer loginStatus;

    /**
     * 状态 1-正常 2-锁定
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 创建时间（注册时间）
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 设备号
     */
    @Column(name = "`device_id`")
    private String deviceId;

    /**
     * 客户端版本号
     */
    @Column(name = "`client_version`")
    private String clientVersion;

    /**
     * 用户令牌
     */
    @Column(name = "`token`")
    private String token;

    /**
     * token失效时间
     */
    @Column(name = "`token_expired`")
    private Long tokenExpired;

    /**
     * 收获地址
     */
    @Column(name = "`address`")
    private String address;

}
