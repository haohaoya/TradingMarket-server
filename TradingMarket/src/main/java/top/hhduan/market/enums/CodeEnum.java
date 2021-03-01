package top.hhduan.market.enums;

/**
 * 响应code的枚举
 *
 * @author duanhaohao
 * @version 1.0
 * @date
 */
public enum CodeEnum {
    /** 描述 */
    SUCCESS(0, "成功", true),
    FAIL(-1, "失败", false),
    PARAMS_ERROR(-2, "参数异常", false),
    TOKEN_FAILURE(999, "令牌失效", false),
    ;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;
    /**
     * 返回状态
     */
    private Boolean flag;

    CodeEnum(Integer code, String message, Boolean flag) {
        this.code = code;
        this.message = message;
        this.flag = flag;
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
