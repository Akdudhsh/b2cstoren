package org.example.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pojo.Cart;
import org.example.pojo.Product;

import java.io.Serializable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 13:33
 * @Description:
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartVo implements Serializable {
    public static final Long serialVersionUID = 1L;
    private Integer id; //购物车id
    private Integer productID; //商品id
    private String productName; // 商品名称
    private String productImg; //商品图片地址
    private Double price; //商品价格
    private Integer num;  //购物车商品数量
    private Integer maxNum; //购物车商品最大数量限制
    private Boolean check = false; //是否勾选
    public CartVo(Product product, Cart cart){
        this.id = cart.getId();
        this.productID = product.getProductId();
        this.productName = product.getProductName();
        this.productImg = product.getProductPicture();
        this.price = product.getProductSellingPrice();
        this.num = cart.getNum();
        this.maxNum = product.getProductNum();
    }
}
