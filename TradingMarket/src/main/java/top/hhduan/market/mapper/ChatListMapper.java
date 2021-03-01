package top.hhduan.market.mapper;

import top.hhduan.market.entity.ChatList;
import top.hhduan.market.vo.ChatVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChatListMapper extends Mapper<ChatList> {
    /**
     * 查询聊天列表
     * @param userId
     * @return
     */
    List<ChatVO> selectChatList(String userId);
}
