package org.example.product.controller;

import org.example.param.*;
import org.example.product.service.ProductService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:54
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 根据类别名称返回热门的7条商品信息
     * @param productPromoParam
     * @param result
     * @return
     */
    @PostMapping("/promo")
    public R promo(@RequestBody @Validated ProductPromoParam productPromoParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不能为null");
        }

        return productService.promo(productPromoParam.getCategoryName());
    }

    /**
     * 根据多个类别名称返回热门的7条商品信息
     * @param productHotsParam
     * @param result
     * @return
     */
    @PostMapping("/hots")
    public R hots(@RequestBody @Validated ProductHotsParam productHotsParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不满足要求");
        }
        return productService.hots(productHotsParam.getCategoryName());
    }
    @PostMapping("/category/list")
    public R categoryList(){
        return productService.categoryList();
    }

    /**
     * 通过指定的商品类别查询商品信息
     * @param productByCategoryParam
     * @param result
     * @return
     */
    @PostMapping("/bycategory")
    public R byCategory(@RequestBody @Validated ProductByCategoryParam productByCategoryParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return productService.byCategory(productByCategoryParam);
    }

    /**
     * 查询所有的商品信息
     * @param productByCategoryParam
     * @param result
     * @return
     */
    @PostMapping("/all")
    public R all(@RequestBody @Validated ProductByCategoryParam productByCategoryParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return productService.all(productByCategoryParam);
    }

    /**
     * 查询某个商品的详细信息
     * @param productDetailParam
     * @param result
     * @return
     */
    @PostMapping("/detail")
    public R detail(@RequestBody @Validated ProductDetailParam productDetailParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return productService.detail(productDetailParam.getProductID());
    }

    /**
     * 查询某个商品的轮番图信息
     * @param productDetailParam
     * @param result
     * @return
     */
    @PostMapping("/pictures")
    public R pictures(@RequestBody @Validated ProductDetailParam productDetailParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数不符合要求");
        }
        return productService.pictures(productDetailParam.getProductID());
    }
    @PostMapping("/search")
    public R search(@RequestBody @Validated SearchProductParam searchProductParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("搜索参数不正确");
        }
        return productService.search(searchProductParam);
    }
}
