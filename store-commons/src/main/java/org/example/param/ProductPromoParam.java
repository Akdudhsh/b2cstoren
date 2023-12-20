package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:24
 * @Description:
 * @Version 1.0
 */
@Data
public class ProductPromoParam {
    @NotBlank
    private String categoryName;
}
