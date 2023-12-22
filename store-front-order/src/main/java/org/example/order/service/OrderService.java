package org.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.param.OrderSaveParam;
import org.example.pojo.Order;
import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/22 15:12
 * @Description:
 * @Version 1.0
 */
public interface OrderService extends IService<Order> {
    R saveOrders(OrderSaveParam orderSaveParam);

    R orderList(Integer userId);
}
