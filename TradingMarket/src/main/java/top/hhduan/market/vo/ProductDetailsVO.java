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
 * @date: 2021/1/20 21:18
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class ProductDetailsVO implements Serializable {

    private static final long serialVersionUID = 2808501232203226702L;

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
     * 发布时间
     */
    private Date publishTime;

    /**
     * 点赞状态 1-点赞 2-未点赞
     */
    private Integer praiseStatus;

    /**
     * 商品编号
     */
    private Integer productId;

    /**
     * 商品图片
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
     * 想要数
     */
    private Integer wantNum;

    /**
     * 商品发布地
     */
    private String productAddress;

}
