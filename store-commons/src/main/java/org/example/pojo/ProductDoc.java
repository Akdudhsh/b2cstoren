package org.example.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 12:48
 * @Description:
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class ProductDoc extends Product{
    public ProductDoc(Product product){
        super(product.getProductId(), product.getProductName(), product.getCategoryId(),
                product.getProductTitle(), product.getProductIntro(), product.getProductPicture(),
                product.getProductPrice(), product.getProductSellingPrice(),
                product.getProductNum(),product.getProductSales());
    }
}
