package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:13
 * @description: 点赞/取消点赞接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class PraiseBO extends BaseBO {

    private static final long serialVersionUID = 3892995581269313674L;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空")
    private Integer productId;

    /**
     * 1-点赞 2-取消点赞
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

}
