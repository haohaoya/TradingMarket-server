package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:42
 * @description: 个人中心商品查询接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class MyProductSearchBO extends BaseBO {
    private static final long serialVersionUID = 1754850472175512237L;

    /**
     * 查询类别 1--我发布的 2--我卖出的 3--我买到的
     */
    @NotNull(message = "type不能为空")
    private Integer type;

}
