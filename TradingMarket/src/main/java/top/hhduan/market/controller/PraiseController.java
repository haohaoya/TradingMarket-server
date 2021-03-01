package top.hhduan.market.controller;

import top.hhduan.market.bo.PraiseBO;
import top.hhduan.market.service.PraiseService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/15 16:57
 * @description: 点赞相关
 * @version: 1.0
 */
@RestController
@Slf4j
public class PraiseController extends BaseController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private UserService userService;


    /**
     * 点赞/取消点赞
     * @param bo
     * @return
     */
    @RequestMapping(value = "praiseOrUnPraise", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO praiseOrUnPraise(@Valid @RequestBody PraiseBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        Assertion.isTrue(1 == bo.getStatus() || 2 == bo.getStatus(), "点赞状态异常");
        praiseService.praiseOrUnPraise(bo);
        return ResponseVO.success();
    }

    /**
     * 获取点赞列表
     * @param bo
     * @return
     */
    @RequestMapping(value = "getPraiseList", method = RequestMethod.GET)
    public ResponseVO getPraiseList(PraiseBO bo) {
        Assertion.notNull(bo, "请求参数不能为空");
        Assertion.isPositive(bo.getProductId(), "商品编号不能为空");
        String token = getToken();
        bo.setToken(token);
        return ResponseVO.success(praiseService.getPraiseList(bo));
    }

}
