package org.example.product.controller;

import org.example.pojo.Product;
import org.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 11:29
 * @Description: 提供给搜索服务的商品控制器类
 * @Version 1.0
 */
@RestController
@RequestMapping("/product")
public class ProductSearchController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public List<Product> list(){
        return productService.list();
    }
}
