package org.example.clients;

import org.example.param.SearchProductParam;
import org.example.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/20 16:18
 * @Description:
 * @Version 1.0
 */
@FeignClient("search-service")
public interface SearchClient {
    @PostMapping("/search/product")
    R search(@RequestBody SearchProductParam searchProductParam);
}
