package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 23:49
 * @description: 发送聊天入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ChatSocketBO extends BaseBO {

    private static final long serialVersionUID = 5720259669569605483L;

    /**
     * 发送对象用户编号
     */
    private String toUserId;

    /**
     * 发送对象用户昵称
     */
    private String toUserName;

    /**
     * 发送对象用户头像
     */
    private String toUserAvatar;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 聊天编号
     */
    private String chatId;

}
