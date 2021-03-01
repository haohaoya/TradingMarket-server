package top.hhduan.market.exception;

import top.hhduan.market.enums.CodeEnum;

/**
 * 业务异常处理
 * @author duanhaohao
 * @version 1.0
 * @date 2019/8/6 10:07
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 2490391672331962902L;
    private Integer code;

    private String message;

    private String[] params;


    public BusinessException(CodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public BusinessException(CodeEnum codeEnum, String... params) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
        this.params = params;
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
