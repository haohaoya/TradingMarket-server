package top.hhduan.market.exception.handler;

import top.hhduan.market.enums.CodeEnum;
import top.hhduan.market.exception.BusinessException;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author duanhaohao
 * @date 2020/8/7 14:07
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ApiError handleException(Throwable e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return new ApiError(CodeEnum.FAIL.getCode(), Detect.notEmpty(e.getMessage()) ? e.getMessage() : "系统异常", request.getHeader("requestId"));
    }


    /**
     * 处理 BusinessException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiError entityBusinessException(BusinessException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return new ApiError(e.getCode(), Detect.notEmpty(e.getMessage()) ? e.getMessage() : CodeEnum.FAIL.getMessage(), request.getHeader("requestId"));
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @returns
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        double a = 0.1;
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        return new ApiError(CodeEnum.PARAMS_ERROR.getCode(),
                str[1] + ":" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), request.getHeader("requestId"));
    }
}
