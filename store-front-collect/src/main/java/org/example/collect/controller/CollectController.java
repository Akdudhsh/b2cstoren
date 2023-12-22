package org.example.collect.controller;

import org.example.collect.service.CollectService;
import org.example.param.CollectListParam;
import org.example.param.CollectSaveParam;
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
 * @Date: 2023/12/21 10:09
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    /**
     * 实现商品的收藏
     * @param collectSaveParam
     * @param result
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody @Validated CollectSaveParam collectSaveParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return collectService.save(collectSaveParam);
    }
    @PostMapping("/list")
    public R list(@RequestBody @Validated CollectListParam collectListParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return collectService.list(collectListParam.getUserId());
    }
    @PostMapping("/remove")
    public R remove(@RequestBody @Validated CollectSaveParam collectSaveParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return collectService.remove(collectSaveParam);
    }
}
