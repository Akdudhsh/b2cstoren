package org.example.search.service;

import org.example.param.SearchProductParam;
import org.example.utils.R;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 15:47
 * @Description:
 * @Version 1.0
 */
public interface SearchService {
    R search(SearchProductParam searchProductParam);
}
