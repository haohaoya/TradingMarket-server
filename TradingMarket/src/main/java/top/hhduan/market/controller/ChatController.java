package top.hhduan.market.controller;

import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.bo.ChatDetailBO;
import top.hhduan.market.bo.InitChatBO;
import top.hhduan.market.service.ChatDetailService;
import top.hhduan.market.service.ChatService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/8/20 20:53
 * @description:
 * @version: 1.0
 */
@RestController
@Slf4j
public class ChatController extends BaseController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatDetailService chatDetailService;

    @Autowired
    private UserService userService;

    /**
     * 查询聊天框列表
     * @param bo
     * @return
     */
    @RequestMapping(value = "getChatList", method = RequestMethod.POST)
    public ResponseVO getChatList(BaseBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        return ResponseVO.successPageInfo(chatService.getChatList(bo));
    }


    /**
     * 查询聊天详情
     * @param bo
     * @return
     */
    @RequestMapping(value = "getChatDetailList", method = RequestMethod.POST)
    public ResponseVO getChatDetailList(@RequestBody ChatDetailBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        Assertion.notEmpty(bo.getChatId(), "chatId不能为空");
        return ResponseVO.successPageInfo(chatService.getChatDetailList(bo));
    }

    @RequestMapping(value = "initChat", method = RequestMethod.POST)
    public ResponseVO initChat(@RequestBody InitChatBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        Assertion.notEmpty(bo.getToUserId(), "toUserId不能为空");
        Assertion.isPositive(bo.getProductId(), "商品编号不能为空");
        String chatId = chatService.init(bo);
        Map<String, String> map = new HashMap<>(16);
        map.put("chatId", chatId);
        return ResponseVO.success(map);

    }

}
