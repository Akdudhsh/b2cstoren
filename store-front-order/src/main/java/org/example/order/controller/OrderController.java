package org.example.order.controller;

import org.example.order.service.OrderService;
import org.example.param.AddressListParam;
import org.example.param.OrderSaveParam;
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
 * @Date: 2023/12/22 15:11
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 实现订单的保存
     * @param orderSaveParam
     * @return
     */
    @PostMapping("/save")
    public R saveOrders(@RequestBody OrderSaveParam orderSaveParam){
        return orderService.saveOrders(orderSaveParam);
    }

    /**
     * 实现订单的查看
     * @param addressListParam userId 进行了参数校验和参数格式化
     * @param result
     * @return
     */
    @PostMapping("/list")
    public R ordersList(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("用户id不正确");
        }
        return orderService.orderList(addressListParam.getUserId());
    }
}
