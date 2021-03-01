package top.hhduan.market.service.impl;

import com.github.pagehelper.PageHelper;
import top.hhduan.market.bo.AddressBO;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.entity.Address;
import top.hhduan.market.entity.User;
import top.hhduan.market.mapper.AddressMapper;
import top.hhduan.market.service.AddressService;
import top.hhduan.market.service.UserService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.utils.Detect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2019/10/15 19:48
 * @description:
 * @version: 1.0
 */
@Service
@Slf4j
public class AddressServiceImpl extends BaseServiceImpl<Address, Integer> implements AddressService {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAddressList(BaseBO bo) {
        User user = userService.checkToken(bo.getToken());
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo("userId", user.getUserId()).andEqualTo("status", 1);
        example.setOrderByClause("is_default_address asc, create_time desc");
        PageHelper.startPage(bo.getPageNum(), bo.getPageSize());
        return addressMapper.selectByExample(example);
    }

    @Override
    public void saveAddress(AddressBO bo) {
        User user = userService.checkToken(bo.getToken());
        Address address = new Address();
        Date date = new Date();
        BeanUtils.copyProperties(bo, address);
        address.setCreateTime(date);
        address.setUpdateTime(date);
        address.setUserId(user.getUserId());
        if (1 == bo.getIsDefaultAddress()) {
            Example example = new Example(Address.class);
            example.createCriteria().andEqualTo("isDefaultAddress", 1);
            Address update = new Address();
            update.setUpdateTime(date);
            update.setIsDefaultAddress(2);
            addressMapper.updateByExampleSelective(update, example);
        }
        addressMapper.insertSelective(address);
    }

    @Override
    public void updateAddress(AddressBO bo) {
        User user = userService.checkToken(bo.getToken());
        Address address = addressMapper.selectByPrimaryKey(bo.getId());
        Assertion.notNull(address, "收货地址不存在");
        Assertion.isTrue(1 == address.getStatus(), "收货地址已被删除");
        Address update = new Address();
        Date date = new Date();
        BeanUtils.copyProperties(bo, update);
        update.setUpdateTime(date);
        if (Detect.isPositive(bo.getIsDefaultAddress()) && 1 == bo.getIsDefaultAddress()) {
            Example example = new Example(Address.class);
            example.createCriteria().andEqualTo("isDefaultAddress", 1).andNotEqualTo("id", bo.getId());
            Address entity = new Address();
            entity.setUpdateTime(date);
            entity.setIsDefaultAddress(2);
            addressMapper.updateByExampleSelective(entity, example);
        }
        addressMapper.updateByPrimaryKeySelective(update);
    }

    @Override
    public void delAddress(AddressBO bo) {
        User user = userService.checkToken(bo.getToken());
        Address address = addressMapper.selectByPrimaryKey(bo.getId());
        Assertion.notNull(address, "收货地址不存在");
        Assertion.isTrue(1 == address.getStatus(), "收货地址已被删除，请勿重复操作");
        Address update = new Address();
        // 执行逻辑删除
        update.setStatus(2);
        update.setUpdateTime(new Date());
        update.setId(bo.getId());
        addressMapper.updateByPrimaryKeySelective(update);
    }
}
