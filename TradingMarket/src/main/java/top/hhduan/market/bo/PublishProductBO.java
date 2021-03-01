package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 11:22
 * @description: 发布商品接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class PublishProductBO extends BaseBO {
    private static final long serialVersionUID = -3481201029921085411L;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品价格
     */
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    /**
     * 商品类别编号
     */
    @NotNull(message = "商品类别不能为空")
    private Integer productTypeId;

    /**
     * 商品地址
     */
    @NotBlank(message = "商品地址不能为空")
    private String productAddress;

}
