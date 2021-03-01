package top.hhduan.market.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/20 21:22
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ProductNumVO implements Serializable {

    private static final long serialVersionUID = 6904609746866882472L;

    /**
     * 发布商品数量
     */
    private Integer publishNum;

    /**
     * 发布商品总金额
     */
    private BigDecimal publishAmount;

    /**
     * 卖出商品数量
     */
    private Integer saleNum;

    /**
     * 卖出商品总金额
     */
    private BigDecimal saleAmount;

    /**
     * 购买商品数量
     */
    private Integer purchaseNum;

    /**
     * 购买商品总金额
     */
    private BigDecimal purchaseAmount;

}
