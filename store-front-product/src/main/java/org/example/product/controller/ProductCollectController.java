package org.example.product.controller;

import org.example.product.service.ProductService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 10:50
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductCollectController {
    @Autowired
    private ProductService productService;

    /**
     * 根据多个商品id的集合查询多个商品信息的集合
     * @param idList
     * @return
     */
    @PostMapping("/collect/byproductids")
    public R byProductIds(@RequestBody List<Object> idList){
        return productService.byProductIds(idList);
    }
}
