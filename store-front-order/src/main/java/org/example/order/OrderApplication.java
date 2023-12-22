package org.example.order;

import org.example.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 11:16
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("org.example.order.mapper")
@EnableFeignClients(clients = {ProductClient.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
