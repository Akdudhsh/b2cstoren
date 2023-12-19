package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 18:56
 * @Description: 该类用来接受并校验用户名参数
 * @Version 1.0
 * Todo: 要使用jsr 303注解进行参数校验
 * @NotBlank 字符串 不能为null 不能为“”
 * @NotNUll  字符串 不能为null
 * @NotEmpty 集合类型 集合中元素不能为null
 */
@Data
public class UserCheckParam {
    @NotBlank
    private String userName;
}
