package top.hhduan.market.controller;

import top.hhduan.market.bo.AddressBO;
import top.hhduan.market.bo.BaseBO;
import top.hhduan.market.service.AddressService;
import top.hhduan.market.utils.Assertion;
import top.hhduan.market.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/10/15 19:50
 * @description:
 * @version: 1.0
 */
@RestController
@Slf4j
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取地址列表
     * @param bo
     * @return
     */
    @RequestMapping(value = "getAddressList", method = RequestMethod.POST)
    public ResponseVO getAddressList(BaseBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        return ResponseVO.successPageInfo(addressService.getAddressList(bo));
    }

    /**
     * 新增收货地址
     * @param bo
     * @return
     */
    @RequestMapping(value = "saveAddress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseVO saveAddress(@RequestBody @Valid AddressBO bo) {
        // 校验token是否为空
        String token = checkToken();
        bo.setToken(token);
        addressService.saveAddress(bo);
        return ResponseVO.success();
    }

    /**
     * 修改收货地址
     * @param bo
     * @return
     */
    @RequestMapping(value = "updateAddress", method = RequestMethod.POST)
    public ResponseVO updateAddress(@RequestBody AddressBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getId(), "地址编号不能为空");
        bo.setToken(token);
        addressService.updateAddress(bo);
        return ResponseVO.success();
    }

    /**
     * 删除收货地址
     * @param bo
     * @return
     */
    @RequestMapping(value = "delAddress", method = RequestMethod.POST)
    public ResponseVO delAddress(@RequestBody AddressBO bo) {
        // 校验token是否为空
        String token = checkToken();
        Assertion.isPositive(bo.getId(), "地址编号不能为空");
        bo.setToken(token);
        addressService.delAddress(bo);
        return ResponseVO.success();
    }
}
