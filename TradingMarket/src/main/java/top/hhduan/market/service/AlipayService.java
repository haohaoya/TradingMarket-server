package top.hhduan.market.service;

import top.hhduan.market.bo.AlipayBO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/9/16 20:47
 * @description:
 * @version: 1.0
 */
public interface AlipayService {

    /**
     * 支付宝支付接口
     * @param bo
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    String alipay(AlipayBO bo);

    String alipayTest(AlipayBO bo);

    /**
     * 支付宝回调通知
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    String alipayNotify(HttpServletRequest request);
}
