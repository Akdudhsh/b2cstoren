package org.example.carousel.controller;

import org.example.carousel.service.CarouselService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 16:36
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    /**
     * 查询轮番图的优先级最高的6条数据
     * @return
     */
    @PostMapping("/list")
    public R list(){
        return carouselService.list();
    }
}
