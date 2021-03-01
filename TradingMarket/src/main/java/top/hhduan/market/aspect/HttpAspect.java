package top.hhduan.market.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author duanhaohao
 * @date: 2020/8/10 17:08
 * @description: 请求log日志记录
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class HttpAspect {
    /**
     * 定义切点为controller目录下的所有类的所有公共方法
     */
    @Pointcut("execution(public * top.duanhaohao.*.controller.*.*(..))")
    public void log() {
    }

    /**
     * 进入方法前先打印下相关参数信息
     *
     * @param joinPoint
     */
    @Before("log()")
    public void beforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // url
            log.info("url={}", request.getRequestURL());
            // method
            log.info("method={}", request.getMethod());
            // ip
            log.info("ip={}", request.getRemoteAddr());
            // 类.方法
            log.info("class.method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()");
            // 请求参数
            log.info("args={}", joinPoint.getArgs());
        }

    }

    @After("log()")
    public void afterLog() {
        log.info("after time={}", new Date());
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void afterReturningLog(Object object) {
        // 判空处理
        log.info("after returning={}", object == null ? "" : object.toString());
    }
}
