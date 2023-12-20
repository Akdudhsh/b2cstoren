package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 23:17
 * @Description:
 * @Version 1.0
 */
@Data
public class ProductDetailParam {
    @NotNull
    private Integer productID;
}
