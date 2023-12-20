package org.example.product.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 13:02
 * @Description:
 * @Version 1.0
 */
@Configuration
public class ProductConfiguration {
    /**
     * 指定mq的序列化方式
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
