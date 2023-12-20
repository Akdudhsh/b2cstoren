package org.example.category;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 18:29
 * @Description:
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("org.example.category.mapper")
@EnableCaching
public class CategoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CategoryApplication.class,args);
    }
    /**
     * mybatis-plus的分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
