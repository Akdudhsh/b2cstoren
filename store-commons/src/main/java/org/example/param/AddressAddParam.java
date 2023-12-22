package org.example.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 17:03
 * @Description:
 * @Version 1.0
 */
@Data
public class AddressAddParam {
    @JsonProperty("user_id")
    private Integer userId;
    private Add add;
}


