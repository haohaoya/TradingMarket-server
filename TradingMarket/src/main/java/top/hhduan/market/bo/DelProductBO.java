package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:46
 * @description: 删除商品入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class DelProductBO extends BaseBO {
    private static final long serialVersionUID = 4626123382408864522L;

    /**
     * 商品编号
     */
    private Integer productId;

    /**
     * 删除类型 1-我发布的 2-我卖出的 3-我购买的
     */
    private Integer type;

}
