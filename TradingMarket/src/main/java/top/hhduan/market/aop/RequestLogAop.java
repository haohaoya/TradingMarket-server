package top.hhduan.market.aop;

import com.alibaba.fastjson.JSONObject;
import top.hhduan.market.entity.RequestLog;
import top.hhduan.market.mapper.RequestLogMapper;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.utils.IpUtils;
import top.hhduan.market.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;


/**
 * 定义一个切面
 * 记录所有请求到日志表
 *
 * @author duanhaohao
 */
@Aspect
@Configuration
@Slf4j
public class RequestLogAop {

    @Autowired
    private RequestLogMapper requestLogMapper;

    private long currentTime = 0L;

    /**
     * 定义切点Pointcut, 所有Controller里所有请求都保存到日志表
     */
    @Pointcut("execution(* top.hhduan.market.controller.*.*(..))")
    public void excudeService() {
    }

    @Before("excudeService()")
    public void before() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestLog requestLog = this.getLog(this.getRequest(), joinPoint, "INFO");
        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        requestLog.setTime(System.currentTimeMillis() - currentTime);
        try {
            requestLogMapper.insertSelective(requestLog);
        } catch (Exception e) {
            log.warn("========== 保存访问日志 error ==========requestLog : {},ERROR :{}", requestLog, e);
        }
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "excudeService()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        RequestLog requestLog = null;
        try {
            requestLog = this.getLog(this.getRequest(), (ProceedingJoinPoint) joinPoint, "ERROR");
            String exceptionDetail = ThrowableUtil.getStackTrace(e);
            requestLog.setExceptionDetail(exceptionDetail.length() > 1800 ? exceptionDetail.substring(0, 1800) : exceptionDetail);
            requestLog.setTime(System.currentTimeMillis() - currentTime);
            requestLogMapper.insertSelective(requestLog);
        } catch (Exception e1) {
            log.warn("========== 保存访问日志 error ==========requestLog : {},ERROR :{}", requestLog, e1);
        }
    }

    /**
     * 获取请求头信息
     *
     * @param request
     * @return
     */
    private String getHeaders(HttpServletRequest request) {
        Enumeration enumeration = request.getHeaderNames();
        JSONObject headerObj = new JSONObject();
        while (enumeration.hasMoreElements()) {
            String headerName = (String) enumeration.nextElement();
            String headerValue = request.getHeader(headerName);
            headerObj.put(headerName, headerValue);
        }
        return headerObj.toString();
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    private String getParams(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        JSONObject paramsObj = new JSONObject();
        // 获取request参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (Detect.notEmpty(parameterMap)) {
            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                StringBuilder value = new StringBuilder();
                for (int i = 0; i < param.getValue().length; i++) {
                    value.append(param.getValue()[i]);
                }
                paramsObj.put(param.getKey(), value.toString().trim());
            }
        }
        // 获取body参数
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                paramsObj.put(argNames[i], argValues[i]);
            }
        }
        return paramsObj.toString();
    }

    private HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    private RequestLog getLog(HttpServletRequest request, ProceedingJoinPoint joinPoint, String logType) {
        RequestLog requestLog = new RequestLog();
        requestLog.setIp(IpUtils.getIp(request));
        requestLog.setUrl(request.getRequestURL().toString());
        try {
            requestLog.setRequestParams(this.getParams(request, joinPoint));
        } catch (Exception e) {
            log.error("body参数异常", e);
        }
        requestLog.setHeaders(this.getHeaders(request));
        requestLog.setRequestTime(new Date());
        requestLog.setRequestType(request.getMethod());
        requestLog.setLogType(logType);
        currentTime = System.currentTimeMillis();
        return requestLog;
    }

}
