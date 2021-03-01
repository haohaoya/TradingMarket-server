package top.hhduan.market.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/12/25 22:05
 * @description:
 * @version: 1.0
 */
@Setter
@Getter
@ToString(callSuper = true)
public class AddressBO extends BaseBO {
    private static final long serialVersionUID = 4995079682860629149L;


    /**
     * 主键
     */
    private Integer id;
    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String consigneeName;

    /**
     * 收货人手机号
     */
    @NotBlank(message = "收货人手机号不能为空")
    private String consigneeMobile;

    /**
     * 省
     */
    @NotBlank(message = "省不能为空")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "市不能为空")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "区不能为空")
    private String district;

    /**
     * 镇
     */
    private String street;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String addressDetail;

    /**
     * 是否默认地址
     */
    @NotNull(message = "是否默认地址不能为空")
    private Integer isDefaultAddress;

}
