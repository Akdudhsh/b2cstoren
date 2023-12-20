package org.example.carousel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kotlin.jvm.internal.Lambda;
import lombok.extern.slf4j.Slf4j;
import org.example.carousel.mapper.CarouselMapper;
import org.example.carousel.service.CarouselService;
import org.example.pojo.Carousel;
import org.example.utils.R;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 16:40
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {
    @Resource
    private CarouselMapper carouselMapper;
    /**
     * @return
     */
    @Override
    @Cacheable(value = "list.carousel",key = "#root.methodName",cacheManager = "cacheManagerDay")
    public R list() {
       LambdaQueryWrapper<Carousel> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.orderByAsc(Carousel::getPriority);
        List<Carousel> list = carouselMapper.selectList(queryWrapper);
        //使用jdk8的stream流切割数据
        List<Carousel> carouselList = list.stream().limit(6).collect(Collectors.toList());
        log.info("CarouselServiceImpl.list业务结束，结果:{}","首页查询轮番图");
        return R.ok(carouselList);
    }
}
