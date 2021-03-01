package top.hhduan.market.exception.handler;

import lombok.Data;

/**
 * @author duanhaohao
 */
@Data
class ApiError {

    private Integer code;
    private Long timestamp;
    private String message;
    private Object result;
    private String requestId;

    private ApiError() {
        timestamp = System.currentTimeMillis();
    }

    ApiError(Integer code, String message, String requestId) {
        this();
        this.code = code;
        this.message = message;
        this.requestId = requestId;
    }

    ApiError(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }
}


