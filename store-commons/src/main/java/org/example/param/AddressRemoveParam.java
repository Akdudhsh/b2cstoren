package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 11:06
 * @Description:
 * @Version 1.0
 */
@Data
public class AddressRemoveParam {
    @NotNull
    private Long id;
}
