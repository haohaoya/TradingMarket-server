package top.hhduan.market.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/20 21:14
 * @description: 登录成功返回参数
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 3653029165858373922L;

    /**
     * 用户令牌
     */
    private String token;

    /**
     * 昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 状态 1-正常 2-锁定
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 收获地址
     */
    private String address;

}
