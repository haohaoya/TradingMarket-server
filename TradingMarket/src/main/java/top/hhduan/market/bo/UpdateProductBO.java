package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 15:32
 * @description: 修改商品信息入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class UpdateProductBO extends BaseBO {

    private static final long serialVersionUID = 7193929719742570346L;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品类别编号
     */
    private Integer productTypeId;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空")
    private Integer productId;

    /**
     * 未变更图片地址
     */
    private String[] oldImgs;

    /**
     * 商品地址
     */
    private String productAddress;

}
