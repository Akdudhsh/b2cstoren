package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 21:33
 * @Description:
 * @Version 1.0
 */
@Data
public class ProductHotsParam {
    @NotEmpty
    private List<String> categoryName;
}
