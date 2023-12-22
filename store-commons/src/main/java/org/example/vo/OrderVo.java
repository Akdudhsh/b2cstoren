package org.example.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.pojo.Order;
import org.example.pojo.Product;

import java.io.Serializable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 15:04
 * @Description: 该类用来返回给订单查看接口返回的数据
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class OrderVo extends Order implements Serializable {
    public static final Long serialVersionUID = 1L;
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("product_picture")
    private String productPicture;
    public OrderVo(Order order, Product product){
        super(order.getId(),order.getOrderId(),order.getUserId()
        , order.getProductId(), order.getProductNum(), order.getProductPrice()
        , order.getOrderTime());
        this.productName = product.getProductName();
        this.productPicture = product.getProductPicture();
    }
}
