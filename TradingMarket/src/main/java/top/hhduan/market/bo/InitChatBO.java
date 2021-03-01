package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 20:09
 * @description: 初始化聊天（点击我想要）入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class InitChatBO extends ChatSocketBO {

    private static final long serialVersionUID = -7080692308595415186L;

    /**
     * 商品编号
     */
    private Integer productId;

}
