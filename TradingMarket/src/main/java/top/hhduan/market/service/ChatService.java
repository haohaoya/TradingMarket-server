package top.hhduan.market.service;

import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.bo.ChatDetailBO;
import top.hhduan.market.bo.ChatSocketBO;
import top.hhduan.market.bo.InitChatBO;
import top.hhduan.market.entity.Chat;
import top.hhduan.market.entity.ChatDetail;
import top.hhduan.market.vo.ChatVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/19 17:51
 * @description: 聊天框相关接口
 * @version: 1.0
 */
public interface ChatService extends BaseService<Chat, Integer> {

    /**
     * 聊天 -- 发送消息
     * @param bo
     */
    @Transactional(rollbackFor = Exception.class)
    void chat(ChatSocketBO bo);

    /**
     * 获取聊天框列表
     * @param bo
     * @return
     */
    List<ChatVO> getChatList(BaseBO bo);

    /**
     * 查询聊天详情
     * @param bo
     * @return
     */
    List<ChatDetail> getChatDetailList(ChatDetailBO bo);

    /**
     * 初始化聊天 -- 点击我想要
     * @param bo
     * @return
     */
    String init(InitChatBO bo);

}
