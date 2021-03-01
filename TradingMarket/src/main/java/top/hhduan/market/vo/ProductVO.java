package top.hhduan.market.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2021/1/20 21:26
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 8821081481707766726L;

    /**
     * 商品编号
     */
    private Integer id;

    /**
     * 商品图片，多个图片地址用英文逗号隔开
     */
    private String productImgs;

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
     * 商品类别名称
     */
    private String productTypeName;

    /**
     * 发布人
     */
    private String publishUserId;

    /**
     * 发布人昵称
     */
    private String publishUserName;

    /**
     * 发布人头像
     */
    private String publishUserAvatar;

    /**
     * 购买人
     */
    private String buyingUserId;

    /**
     * 交易状态 1-未交易 2-交易中 3-交易成功 4-交易失败
     */
    private Integer tradeStatus;

    /**
     * 创建时间（发布时间）
     */
    private Date createTime;

    /**
     * 交易时间（购买时间）
     */
    private Date tradeTime;

    /**
     * 想要人数
     */
    private Integer wantNum;

    /**
     * 订单编号
     */
    private Integer orderId;

    /**
     * 商品地址
     */
    private String productAddress;

}
