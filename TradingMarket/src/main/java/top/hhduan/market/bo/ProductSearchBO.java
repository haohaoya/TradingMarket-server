package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 16:42
 * @description: 首页商品查询接口入参
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ProductSearchBO extends BaseBO {
    private static final long serialVersionUID = 1754850472175512237L;

    /**
     * 商品名称
     */
    private String productDesc;

    /**
     * 商品类别
     */
    private Integer productTypeId;

}
