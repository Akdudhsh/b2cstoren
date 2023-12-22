package org.example.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.param.CartParam;
import org.example.pojo.Cart;
import org.example.utils.R;

import java.util.List;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/21 16:42
 * @Description:
 * @Version 1.0
 */
public interface CartService {
    R save(CartParam cartParam);

    R list(Integer userId);

    R update(CartParam cartParam);

    R delete(CartParam cartParam);

    Integer deleteByIds(List<Integer> idList);
}
