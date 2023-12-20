package org.example.category.service;

import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/19 19:32
 * @Description:
 * @Version 1.0
 */
public interface CategoryService {
    R getIdByName(String name);

    R list();
}
