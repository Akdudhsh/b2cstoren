package org.example.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 13:34
 * @Description:
 * @Version 1.0
 */
@MapperScan(basePackages = "org.example.user.mapper")
@SpringBootApplication
@EnableCaching
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
