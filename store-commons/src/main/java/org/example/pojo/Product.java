package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:25
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("category_id")
    private Integer categoryId;

    /**
     * 手机title
     */
    @JsonProperty("product_title")
    private String productTitle;

    /**
     * 手机信息描述
     */
    @JsonProperty("product_intro")
    private String productIntro;

    @JsonProperty("product_picture")
    private String productPicture;

    /**
     * 商品价格
     */
    @JsonProperty("product_price")
    private Double productPrice;

    /**
     * 售卖价格
     */
    @JsonProperty("product_selling_price")
    private Double productSellingPrice;

    /**
     * 商品库存
     */
    @JsonProperty("product_num")
    private Integer productNum;

    /**
     * 已卖数量
     */
    @JsonProperty("product_sales")
    private Integer productSales;
}
