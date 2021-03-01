package top.hhduan.market.service;

import top.hhduan.market.bo.AddressBO;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.entity.Address;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/10/15 19:47
 * @description:
 * @version: 1.0
 */
public interface AddressService extends BaseService<Address, Integer> {
    /**
     * 获取地址列表
     * @param bo
     * @return
     */
    List<Address> getAddressList(BaseBO bo);

    /**
     * 保存地址
     * @param bo
     */
    void saveAddress(AddressBO bo);

    /**
     * 修改收货地址
     * @param bo
     */
    void updateAddress(AddressBO bo);

    /**
     * 删除收货地址
     * @param bo
     */
    void delAddress(AddressBO bo);
}
