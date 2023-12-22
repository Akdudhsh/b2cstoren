package org.example.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 13:26
 * @Description:
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartParam {
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("user_id")
    private Integer userId;
    private Integer num;
}
