package org.example.cart.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.cart.service.CartService;
import org.example.pojo.Cart;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 15:52
 * @Description:
 * @Version 1.0
 */
@Component
@Slf4j
public class CartRabbitMQListener {
    @Autowired
    private CartService cartService;

    /**
     * 监听订单服务发送过来需要清除的购物车项
     * @param cartIdList
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "cart.deleteOrderItem.queue"),
            exchange = @Exchange(value = "store.order"),
            key = {"order.cart"}
    ))
    public void deleteCartItem(List<Integer> cartIdList){
        Integer rows = cartService.deleteByIds(cartIdList);
        log.info("CartRabbitMQListener.deleteCartItem业务结束，结果: 删除了{}项",rows);
    }
}
