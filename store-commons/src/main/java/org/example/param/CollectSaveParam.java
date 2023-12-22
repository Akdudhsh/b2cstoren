package org.example.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 10:06
 * @Description:
 * @Version 1.0
 */
@Data
public class CollectSaveParam {
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("user_id")
    private Integer userId;
}
