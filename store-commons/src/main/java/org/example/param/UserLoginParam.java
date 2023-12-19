package org.example.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 21:59
 * @Description: 该类用来接受并校验用户登录所需的参数
 * @Version 1.0
 */
@Data
public class UserLoginParam {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
