package org.example.collect;

import org.example.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 9:37
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "org.example.collect.mapper")
@EnableFeignClients(clients = {ProductClient.class})
@EnableCaching
public class CollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class,args);
    }
}
