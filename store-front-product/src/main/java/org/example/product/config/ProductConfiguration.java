package org.example.product.config;

import org.example.config.CacheConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 13:02
 * @Description:
 * @Version 1.0
 */
@Configuration
public class ProductConfiguration extends CacheConfiguration {
    /**
     * 指定mq的序列化方式
     * @return
     */
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
}
