package org.example.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.category.mapper.CategoryMapper;
import org.example.category.service.CategoryService;
import org.example.pojo.Category;
import org.example.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:33
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /** 根据类别名称查询类别id
     * @param name
     * @return
     */
    @Override
    public R getIdByName(String name) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getCategoryName,name);
        Category category = categoryMapper.selectOne(queryWrapper);
        Integer categoryId = null;
        if(category != null) {
           categoryId = category.getCategoryId();
        }
        log.info("CategoryServiceImpl.getIdByName业务结束，结果:{}","查询成功");
        return R.ok("查询成功",categoryId);
    }

    /**
     * @return
     */
    @Override
    public R list() {
        List<Category> list = categoryMapper.selectList(null);
        log.info("CategoryServiceImpl.list业务结束，结果:{}",list);
        return R.ok("查询所有类别成功",list);
    }
}
