package top.hhduan.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "`request_log`")
public class RequestLog {
    /**
     * 自增主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 请求方ip
     */
    @Column(name = "`ip`")
    private String ip;

    /**
     * 请求路径
     */
    @Column(name = "`url`")
    private String url;

    /**
     * 头部信息
     */
    @Column(name = "`headers`")
    private String headers;

    /**
     * 请求类型
     */
    @Column(name = "`request_type`")
    private String requestType;

    /**
     * 请求参数
     */
    @Column(name = "`request_params`")
    private String requestParams;

    /**
     * 请求时间
     */
    @Column(name = "`request_time`")
    private Date requestTime;

    /**
     * 异常信息
     */
    @Column(name = "`exception_detail`")
    private String exceptionDetail;

    /**
     * 日志级别 INFO;ERROR
     */
    @Column(name = "`log_type`")
    private String logType;

    /**
     * 请求时长
     */
    @Column(name = "`time`")
    private Long time;

}
