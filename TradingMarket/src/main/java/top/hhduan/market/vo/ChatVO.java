package top.hhduan.market.vo;

import top.hhduan.market.entity.ChatList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/20 21:06
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ChatVO extends ChatList implements Serializable {

    private static final long serialVersionUID = 8379355470056464837L;

    /**
     * 最后一条聊天记录
     */
    private String lastChatContent;

    /**
     * 商品编号
     */
    private Integer productId;

    /**
     * 商品图片
     */
    private String productImgs;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

}
