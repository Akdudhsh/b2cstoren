package org.example.cart;

import org.example.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 13:01
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "org.example.cart.mapper")
@EnableFeignClients(clients = {ProductClient.class})
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class,args);
    }
}
