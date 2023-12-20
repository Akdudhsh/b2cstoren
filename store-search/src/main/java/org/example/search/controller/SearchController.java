package org.example.search.controller;



import org.example.param.SearchProductParam;
import org.example.search.service.SearchService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 15:46
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 完成商品的搜索服务
     * @param searchProductParam
     * @param result
     * @return
     */
    @PostMapping("/product")
    public R search(@RequestBody @Validated SearchProductParam searchProductParam, BindingResult result){

        if(result.hasErrors()){
            return R.fail("搜索参数不符合要求");
        }
        return searchService.search(searchProductParam);

    }


}
