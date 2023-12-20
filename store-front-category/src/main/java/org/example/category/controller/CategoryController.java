package org.example.category.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.example.category.service.CategoryService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:31
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 通过类别名称查询类别id
     * @param name
     * @return
     */
    @GetMapping("/promo/{categoryName}")
    public R getIdByName(@PathVariable("categoryName") String name){
        if(StringUtils.isBlank(name)){
            return R.fail("参数不能为空");
        }
        return categoryService.getIdByName(name);
    }

    /**
     * 查询所有类别的信息
     * @return
     */
    @GetMapping("/list")
    public R list(){
        return categoryService.list();
    }

}
