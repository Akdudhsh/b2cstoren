package org.example.clients;

import org.example.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:47
 * @Description:
 * @Version 1.0
 */
@FeignClient("category-service")
public interface CategoryClient {
    @GetMapping("/category/promo/{categoryName}")
    R getIdByName(@PathVariable("categoryName") String name);
    @GetMapping("/category/list")
    R list();
}
