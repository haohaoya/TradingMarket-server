package top.hhduan.market.common;

import lombok.*;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/11/9 22:34
 * @description: 请求头部信息
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Header implements Serializable {


    private static final long serialVersionUID = -3416573405008469238L;
    /**
     * 请求流水号
     */
    private String requestId;
    /**
     * 设备号
     */
    private String deviceId;
    /**
     * 客户端版本号
     */
    private String clientVersion;
    /**
     * 当前时间戳
     */
    private Long timestamp;

    /**
     * 用户令牌
     */
    private String token;

}
