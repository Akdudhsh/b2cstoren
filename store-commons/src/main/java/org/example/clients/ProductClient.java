package org.example.clients;

import org.example.param.ProductDetailParam;
import org.example.pojo.Product;
import org.example.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/product/collect/byproductids")
    R byProductIds(@RequestBody List<Object> idList);
    @PostMapping("/product/detail")
    R detail(@RequestBody ProductDetailParam productDetailParam);
}
