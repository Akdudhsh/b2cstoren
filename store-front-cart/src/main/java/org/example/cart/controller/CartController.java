package org.example.cart.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.example.cart.service.CartService;
import org.example.param.CartParam;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 16:42
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     * @param cartParam
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody CartParam cartParam){
        if(cartParam.getUserId() != null &&cartParam.getProductId() != null){
            return cartService.save(cartParam);
        }
        return R.fail("参数不正确");
    }

    /**
     * 查看购物车
     * @param cartParam
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestBody CartParam cartParam){
        if(cartParam.getUserId() != null){
            return cartService.list(cartParam.getUserId());
        }
        return R.fail("参数不正确");
    }

    /**
     * 修改购物车的数量
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody CartParam cartParam){
        if(cartParam.getUserId() != null &&cartParam.getProductId() != null && cartParam.getNum() != null){
            return cartService.update(cartParam);
        }
        return R.fail("参数不正确");
    }

    /**
     * 删除购物车
     * @param cartParam
     * @return
     */
    @PostMapping("/remove")
    public R remove(@RequestBody CartParam cartParam){
        if(cartParam.getUserId() != null &&cartParam.getProductId() != null){
            return cartService.delete(cartParam);
        }
        return R.fail("参数不正确");
    }


}
