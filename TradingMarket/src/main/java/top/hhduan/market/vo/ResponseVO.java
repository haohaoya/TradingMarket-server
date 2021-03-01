package top.hhduan.market.vo;

import com.github.pagehelper.PageInfo;
import top.hhduan.market.enums.CodeEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口公共响应的实体
 *
 * @author 段浩浩
 * @version 1.0
 * @date 2021/1/21 11:07
 */
public class ResponseVO implements Serializable {

    private static final long serialVersionUID = -550518465152474021L;
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应的结果
     */
    private Object result;

    /**
     * 入参请求流水号
     */
    private String requestId;

    /**
     * 时间戳
     */
    private Long timestamp;

    private ResponseVO() {
        this.code = CodeEnum.SUCCESS.getCode();
        this.message = CodeEnum.SUCCESS.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    private ResponseVO(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 通用型成功（无数据）
     * @param requestId
     *          请求时流水号
     * @return
     */
    public static ResponseVO success() {
        return new ResponseVO.Builder().build();
    }

    /**
     * 分页数据封装
     *
     * @param requestId
     *          请求时流水号
     * @param list
     *          数据列表
     * @return
     */
    public static ResponseVO successPageInfo(List<?> list) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("list", list);
        map.put("total", Math.toIntExact(new PageInfo<>(list).getTotal()));
        return new ResponseVO.Builder().result(map).build();
    }

    /**
     * 直接返回result
     *
     * @param requestId
     *          请求时流水号
     * @param result
     *          返回数据
     * @return
     */
    public static ResponseVO success(Object result) {
        return new ResponseVO.Builder().result(result).build();
    }

    /**
     * 统一异常返回
     *
     * @param requestId
     *          请求时流水号
     * @param code
     *          code枚举
     * @return
     */
    public static ResponseVO fail(String requestId, CodeEnum code) {
        return new ResponseVO.Builder().code(code.getCode()).message(code.getMessage()).build();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", requestId='" + requestId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder {
        private ResponseVO responseVO = new ResponseVO();

        private Builder() {}

        private ResponseVO.Builder code(Integer code) {
            this.responseVO.setCode(code);
            return this;
        }

        private ResponseVO.Builder message(String message) {
            this.responseVO.setMessage(message);
            return this;
        }

        private ResponseVO.Builder result(Object result) {
            this.responseVO.setResult(result);
            return this;
        }

        private ResponseVO.Builder requestId(String requestId) {
            this.responseVO.setRequestId(requestId);
            return this;
        }

        public ResponseVO.Builder timestamp(Long timestamp) {
            this.responseVO.setTimestamp(timestamp);
            return this;
        }

        private ResponseVO build() {
            return this.responseVO;
        }

    }
}
