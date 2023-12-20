package org.example.clients;

import org.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/20 11:33
 * @Description:
 * @Version 1.0
 */
@FeignClient("product-service")
public interface ProductClient {
    @GetMapping("/product/list")
    List<Product> list();
}
