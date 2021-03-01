package top.hhduan.market.controller;

import top.hhduan.market.common.Header;
import top.hhduan.market.enums.CodeEnum;
import top.hhduan.market.exception.BusinessException;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/10 0:01
 * @description: 基础服务controller
 * @version: 1.0
 */
@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取头部信息
     * @return
     */
    public Header getHeader() {
        String requestId = request.getHeader("requestId");
        String timestamp = request.getHeader("timestamp");
        String deviceId = request.getHeader("deviceId");
        String clientVersion = request.getHeader("clientVersion");
        String token = request.getHeader("token");
        Header header = new Header(requestId, deviceId, clientVersion, Detect.notEmpty(timestamp) ? Long.valueOf(timestamp) : 0L, token);
        log.info("header信息：" + header.toString());
        return header;
    }

    /**
     * 校验token是否为空
     * @return
     */
    public String checkToken() {
        Header header = getHeader();
        Assertion.notNull(header, "请求头header不能为空");
        String token = header.getToken();
        if (!Detect.notEmpty(token)) {
            throw new BusinessException(CodeEnum.TOKEN_FAILURE);
        }
        return token;
    }

    /**
     * 获取token
     * @return
     */
    public String getToken() {
        Header header = getHeader();
        return header.getToken();
    }
}
