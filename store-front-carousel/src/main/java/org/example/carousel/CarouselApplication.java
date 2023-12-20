package org.example.carousel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 16:28
 * @Description:
 * @Version 1.0
 */
@MapperScan(basePackages = "org.example.carousel.mapper")
@SpringBootApplication
@EnableCaching
public class CarouselApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class,args);
    }
}
