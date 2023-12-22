package org.example.user.controller;

import org.example.param.AddressAddParam;
import org.example.param.AddressListParam;
import org.example.param.AddressRemoveParam;
import org.example.pojo.Address;
import org.example.user.service.AddressService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 10:01
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 完成地址的查询
     * @param addressListParam
     * @param result
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){
        boolean b = result.hasErrors();
        if(b){
            return R.fail("传入参数不正确,查询失败");
        }
        return addressService.list(addressListParam);
    }

    /**
     * 完成地址的添加
     * @param
     * @param result
     * @return
     */
    @PostMapping("/save")
    public R list(@RequestBody @Validated AddressAddParam addressAddParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("传入参数不正确,保存失败");
        }
        return addressService.save(addressAddParam);
    }

    /**
     * 删除地址信息
     * @param addressRemoveParam
     * @param result
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("传入参数不正确,删除失败");
        }
        return addressService.remove(addressRemoveParam.getId());
    }
}
