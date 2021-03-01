package top.hhduan.market.controller;

import top.hhduan.market.bo.AlipayBO;
import top.hhduan.market.config.AlipayConfig;
import top.hhduan.market.service.AlipayService;
import top.hhduan.market.utils.Assertion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/9/16 20:31
 * @description: 支付宝相关接口
 * @version: 1.0
 */
@RestController
@Slf4j
public class AlipayController extends BaseController {

    @Autowired
    private AlipayService alipayService;

    /**
     * 支付宝网页支付
     *
     * @param bo：
     * @param response
     */
    @RequestMapping(value = "alipay", method = {RequestMethod.POST, RequestMethod.GET})
    public void alipay(AlipayBO bo, HttpServletResponse response) {
        // 校验token是否为空
        Assertion.isPositive(bo.getOrderId(), "订单号不能为空");
        Assertion.notEmpty(bo.getToken(), "token不能为空");
        String form = alipayService.alipay(bo);
        try {
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            //直接将完整的表单html输出到页面
            PrintWriter out = response.getWriter();
            out.write(form);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("支付宝调用支付接口异常", e);
        }
    }

    /**
     * 支付宝异步通知 notify_url
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "alipayNotify", method = RequestMethod.POST)
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            String res = alipayService.alipayNotify(request);
            log.info("支付回调res:" + res);
            out.println(res);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("支付宝回调异常", e);
        }
    }


    @RequestMapping(value = "alipayTest", method = {RequestMethod.POST, RequestMethod.GET})
    public void alipayTest(AlipayBO bo, HttpServletResponse response) {
        String form = alipayService.alipayTest(bo);
        try {
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            //直接将完整的表单html输出到页面
            PrintWriter out = response.getWriter();
            out.write(form);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("支付宝调用支付接口异常", e);
        }
    }
}
