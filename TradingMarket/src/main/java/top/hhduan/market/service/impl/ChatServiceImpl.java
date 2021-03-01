package top.hhduan.market.service.impl;

import com.github.pagehelper.PageHelper;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.bo.ChatDetailBO;
import top.hhduan.market.bo.ChatSocketBO;
import top.hhduan.market.bo.InitChatBO;
import top.hhduan.market.entity.*;
import top.hhduan.market.mapper.ChatDetailMapper;
import top.hhduan.market.mapper.ChatListMapper;
import top.hhduan.market.mapper.ChatMapper;
import top.hhduan.market.mapper.ProductMapper;
import top.hhduan.market.service.ChatService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.socket.ChatSocket;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import top.hhduan.market.utils.UUIDUtils;
import top.hhduan.market.vo.ChatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/19 17:52
 * @description: 聊天框相关业务
 * @version: 1.0
 */
@Service
@Slf4j
public class ChatServiceImpl extends BaseServiceImpl<Chat, Integer> implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private ChatListMapper chatListMapper;

    @Autowired
    private ChatDetailMapper chatDetailMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatSocket chatSocket;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void chat(ChatSocketBO bo) {
        User user = userService.checkToken(bo.getToken());
        Example example = new Example(Chat.class);
        example.createCriteria().andEqualTo("id", bo.getChatId());
        List<Chat> list = chatMapper.selectByExample(example);
        Date date = new Date();
        String chatId;
        // 第一次聊天
        if (!Detect.notEmpty(list)) {
            // 初始化聊天
            chatId = UUIDUtils.getUid();
            initChat(bo, chatId, null, user, date, 1);
        } else {
            // 不在线 未读数+1
            Chat chat = Detect.firstOne(list);
            chatId = null == chat ? null : chat.getId();
            if (!chatSocket.online(bo.getToUserId())) {
                Assertion.notNull(chat, "数据异常，发送失败");
                Example chatDetailExample = new Example(ChatDetail.class);
                chatDetailExample.createCriteria().andEqualTo("chatId", chatId)
                        .andEqualTo("userId", bo.getToUserId());
                List<ChatList> chatLists = chatListMapper.selectByExample(chatDetailExample);
                Assertion.notEmpty(chatLists, "数据异常，发送失败");
                ChatList chatList = Detect.firstOne(chatLists);
                Assertion.notNull(chatList, "数据异常，发送失败");
                ChatList update = new ChatList();
                update.setUnread(chatList.getUnread() + 1);
                update.setId(chatList.getId());
                update.setUpdateTime(date);
                chatListMapper.updateByPrimaryKeySelective(update);
            }
            // 将最后一条消息标识去掉
            Example detailExample = new Example(ChatDetail.class);
            detailExample.createCriteria().andEqualTo("chatId", chatId).andEqualTo("isLatest", 1);
            ChatDetail update = new ChatDetail();
            update.setIsLatest(0);
            update.setUpdateTime(date);
            chatDetailMapper.updateByExampleSelective(update, detailExample);
        }
        // 处理聊天详情
        ChatDetail chatDetail = new ChatDetail();
        chatDetail.setChatId(chatId);
        chatDetail.setContent(bo.getContent());
        chatDetail.setCreateTime(date);
        chatDetail.setIsLatest(1);
        chatDetail.setUpdateTime(date);
        chatDetail.setUserAvatar(user.getUserAvatar());
        chatDetail.setUserName(user.getUserName());
        chatDetail.setUserId(user.getUserId());
        chatDetailMapper.insertSelective(chatDetail);
    }

    /**
     * 初始化聊天
     *
     * @param bo
     * @param chatId
     * @param productId
     * @param user
     * @param date
     * @param unread
     */
    private void initChat(ChatSocketBO bo, String chatId, Integer productId, User user, Date date, Integer unread) {
        Chat chat = new Chat();
        chat.setAnotherUserId(bo.getToUserId());
        chat.setUserId(user.getUserId());
        chat.setId(chatId);
        chat.setProductId(productId);
        chatMapper.insertSelective(chat);
        ChatList chatList = new ChatList();
        chatList.setAnotherUserId(bo.getToUserId());
        chatList.setAnotherUserAvatar(bo.getToUserAvatar());
        chatList.setAnotherUserName(bo.getToUserName());
        chatList.setCreateTime(date);
        chatList.setUpdateTime(date);
        chatList.setStatus(1);
        // 在线
        if (chatSocket.online(user.getUserId())) {
            chatList.setIsOnline(1);
        } else {
            // 不在线
            chatList.setIsOnline(2);
        }
        chatList.setUserAvatar(user.getUserAvatar());
        chatList.setUserId(user.getUserId());
        chatList.setUserName(user.getUserName());
        chatList.setChatId(chatId);
        if (Detect.isPositive(productId)) {
            chatList.setProductId(productId);
        }
        chatListMapper.insertSelective(chatList);
        ChatList secondChatList = new ChatList();
        secondChatList.setUserId(bo.getToUserId());
        secondChatList.setUserAvatar(bo.getToUserAvatar());
        secondChatList.setUserName(bo.getToUserName());
        secondChatList.setAnotherUserId(user.getUserId());
        secondChatList.setAnotherUserAvatar(user.getUserAvatar());
        secondChatList.setAnotherUserName(user.getUserName());
        // 不在线
        if (!chatSocket.online(bo.getToUserId())) {
            secondChatList.setUnread(unread);
            secondChatList.setIsOnline(2);
        } else {
            // 在线
            secondChatList.setIsOnline(1);
        }
        secondChatList.setChatId(chatId);
        secondChatList.setCreateTime(date);
        secondChatList.setUpdateTime(date);
        secondChatList.setStatus(1);
        if (Detect.isPositive(productId)) {
            secondChatList.setProductId(productId);
        }
        chatListMapper.insertSelective(secondChatList);
    }

    @Override
    public List<ChatVO> getChatList(BaseBO bo) {
        User user = userService.checkToken(bo.getToken());
        PageHelper.startPage(bo.getPageNum(), bo.getPageSize());
        return chatListMapper.selectChatList(user.getUserId());
    }

    @Override
    public List<ChatDetail> getChatDetailList(ChatDetailBO bo) {
        User user = userService.checkToken(bo.getToken());
        // 将未读数变为0
        ChatList chatList = new ChatList();
        chatList.setUnread(0);
        Example chatListExample = new Example(ChatList.class);
        chatListExample.createCriteria().andEqualTo("chatId", bo.getChatId())
                .andEqualTo("anotherUserId", user.getUserId());
        chatListMapper.updateByExampleSelective(chatList, chatListExample);
        PageHelper.startPage(bo.getPageNum(), bo.getPageSize());
        Example example = new Example(ChatDetail.class);
        example.createCriteria().andEqualTo("chatId", bo.getChatId());
        example.setOrderByClause(" create_time desc");
        return chatDetailMapper.selectByExample(example);
    }

    @Override
    public String init(InitChatBO bo) {
        User user = userService.checkToken(bo.getToken());
        Product product = productMapper.selectByPrimaryKey(bo.getProductId());
        Assertion.notNull(product, "商品信息不存在");
        Example example = new Example(Chat.class);
        example.createCriteria().andEqualTo("userId", user.getUserId())
                .andEqualTo("anotherUserId", bo.getToUserId())
                .andEqualTo("productId", bo.getProductId());
        List<Chat> list = chatMapper.selectByExample(example);
        String chatId = null;
        if (!Detect.notEmpty(list)) {
            Example example2 = new Example(Chat.class);
            example2.createCriteria().andEqualTo("anotherUserId", user.getUserId())
                    .andEqualTo("userId", bo.getToUserId())
                    .andEqualTo("productId", bo.getProductId());
            list = chatMapper.selectByExample(example2);
            if (!Detect.notEmpty(list)) {
                User toUser = userService.findById(bo.getToUserId());
                Assertion.notNull(toUser, "对方用户不存在");
                chatId = UUIDUtils.getUid();
                bo.setToUserName(toUser.getUserName());
                bo.setToUserAvatar(toUser.getUserAvatar());
                initChat(bo, chatId, bo.getProductId(), user, new Date(), 0);
            } else {
                Chat chat = Detect.firstOne(list);
                if (chat != null) {
                    chatId = chat.getId();
                }
            }
        } else {
            Chat chat = Detect.firstOne(list);
            if (chat != null) {
                chatId = chat.getId();
            }
        }
        return chatId;
    }
}
