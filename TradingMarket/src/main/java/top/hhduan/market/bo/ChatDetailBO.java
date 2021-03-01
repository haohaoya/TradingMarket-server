package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 23:35
 * @description: 查询聊天详情入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ChatDetailBO extends BaseBO {

    private static final long serialVersionUID = 8732608165375370704L;

    /**
     * 聊天编号
     */
    private String chatId;

}
