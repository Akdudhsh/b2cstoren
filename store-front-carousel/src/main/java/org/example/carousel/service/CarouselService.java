package org.example.carousel.service;

import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/19 16:37
 * @Description:
 * @Version 1.0
 */
public interface CarouselService {
    /**
     * 查询轮番图优先级最高的6条数据
     * @return
     */
    R list();
}
