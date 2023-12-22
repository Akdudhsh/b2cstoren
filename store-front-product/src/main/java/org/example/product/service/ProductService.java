package org.example.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.param.ProductByCategoryParam;
import org.example.param.SearchProductParam;
import org.example.pojo.Product;
import org.example.utils.R;

import java.util.List;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/19 19:54
 * @Description:
 * @Version 1.0
 */
public interface ProductService extends IService<Product> {
    R promo(String categoryName);

    R hots(List<String> categoryName);

    R categoryList();

    R byCategory(ProductByCategoryParam productByCategoryParam);

    R all(ProductByCategoryParam productByCategoryParam);

    R detail(Integer productID);

    R pictures(Integer productID);

    List<Product> list();

    R search(SearchProductParam searchProductParam);

    R byProductIds(List idList);
}
