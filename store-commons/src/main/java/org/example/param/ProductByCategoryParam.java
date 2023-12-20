package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 22:37
 * @Description:
 * @Version 1.0
 */
@Data
public class ProductByCategoryParam {
    @NotNull
    private Integer[] categoryID;

    private Integer currentPage = 1;

    private Integer pageSize = 15;
}
