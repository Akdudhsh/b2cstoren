package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 15:43
 * @Description:
 * @Version 1.0
 */
@Data
public class SearchProductParam {
    @NotBlank
    private String search;
    private Integer currentPage = 1;
    private Integer pageSize = 15;
}
