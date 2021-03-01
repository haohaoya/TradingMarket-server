package top.hhduan.market.socket;

import com.alibaba.fastjson.JSON;
import top.hhduan.market.bo.ChatSocketBO;
import top.hhduan.market.service.ChatService;
import top.hhduan.market.utils.JacksonUtils;
import top.hhduan.market.utils.SpringContextUtil;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duanhaohao
 * @date 2019/8/18
 */
@ServerEndpoint("/socket/")
@Component
@Slf4j
@EqualsAndHashCode
public class ChatSocket {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketTest对象。
     */
    private static CopyOnWriteArraySet<ChatSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * ConcurrentHashMap是线程安全k-v组合集合
     */
    private static ConcurrentHashMap<String, ChatSocket> concurrentHashMap = new ConcurrentHashMap<>(16);

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        //利用客户编号绑定唯一对应通道
        concurrentHashMap.putIfAbsent(session.getQueryString(), this);
        //在线数加1
        addOnlineCount();
        log.info("有新连接加入【{}】！当前在线人数为" + getOnlineCount(), session.getQueryString());
        try {
            sendMessage("有新连接加入！" + session.getId());
            // 未读消息数变为0
            ChatService chatService = SpringContextUtil.getBean(ChatService.class);
        } catch (IOException e) {
            log.error("IO异常", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        /*移除此通道*/
        concurrentHashMap.remove(this.session.getQueryString());
        /*在线数减1*/
        subOnlineCount();
        log.info("通道为【" + this.session.getQueryString() + "】的连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("来自客户端的消息:" + message);
        String reg = "{";
        if (message.contains(reg)) {
            ChatSocketBO vo = JSON.parseObject(message, ChatSocketBO.class);
            // 发送消息到客户端
            sendInfo(vo);
            // 消息记录入库
            try {
                ChatService chatService = SpringContextUtil.getBean(ChatService.class);
                chatService.chat(vo);
            } catch (Exception e) {
                log.warn("消息入库失败", e);
            }
        }
//        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发生错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * @param vo
     * @throws IOException
     */
    public static void sendInfo(ChatSocketBO vo) throws IOException {
        //获取唯一通道
        ChatSocket socket = concurrentHashMap.get(vo.getToUserId());
        String message = JacksonUtils.objectToJson(vo);
        if (socket != null) {
            log.info("开始发送socket消息，message=" + message);
            socket.sendMessage(message);
        } else {
            log.info("未找到socket通道，不发送消息，message=" + message);
        }
    }

    /**
     * 判断用户是否在线
     * @param userId
     * @return
     */
    public boolean online(String userId) {
        return concurrentHashMap.containsKey(userId);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        ChatSocket.onlineCount.incrementAndGet();
    }

    public static synchronized void subOnlineCount() {
        ChatSocket.onlineCount.decrementAndGet();
    }
}
