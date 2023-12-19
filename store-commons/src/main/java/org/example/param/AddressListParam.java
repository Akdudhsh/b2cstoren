package org.example.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 9:59
 * @Description:
 * @Version 1.0
 */
@Data
public class AddressListParam {
    @NotNull
    @JsonProperty("user_id")
    private Integer userId;
}
