package org.example.search;

import org.example.pojo.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 18:18
 * @Description:
 * @Version 1.0
 */
@SpringBootTest
public class SearchApplicationTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @org.junit.jupiter.api.Test
    public void testDataSynInsert(){
        rabbitTemplate.convertAndSend("store.topic","product.insert"
        ,new Product(36,"电脑",8,
                        "满血续航","niubi", "public/xxx",
                        new Double(5000),new Double(4500),20,10));
    }
    @org.junit.jupiter.api.Test
    public void testDataSynDelete(){
        rabbitTemplate.convertAndSend("store.topic","product.delete"
                ,35);
    }
}
