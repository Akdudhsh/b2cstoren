package org.example.user.controller;

import org.example.param.UserCheckParam;
import org.example.param.UserLoginParam;
import org.example.pojo.User;
import org.example.user.service.UserService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 18:00
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 检查用户名是否可用的接口
     * @param userCheckParam 接受检查账号的实体类型 内部有参数校验
     * @param result  参数校验的结果对象
     * @return 返回封装结果的对象R
     */
    @PostMapping("/check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result) {
        //判断校验结果
        boolean b = result.hasErrors();
        if(b){
            return R.fail("用户名不能为空");
        }
        return userService.check(userCheckParam);
    }

    /**
     * 注册用户的接口
     * @param user 接受注册用户的请求参数 内部有参数校验
     * @param result 参数校验的结果
     * @return 返回封装结果的对象R
     */
    @PostMapping("/register")
    public R register(@RequestBody @Validated User user,BindingResult result){
        boolean b = result.hasErrors();
        if(b){
            return R.fail("参数不符合规则,请重新输入");
        }
        return userService.register(user);
    }

    /**
     * 登录用户的接口
     * @param userLoginParam 接受用户登录的参数 内部进行了参数校验
     * @param result 封装了校验结果的对象
     * @return 返回封装结果的对象R
     */
    @PostMapping("/login")
    public  R login(@RequestBody @Validated UserLoginParam userLoginParam,BindingResult result){
        boolean b = result.hasErrors();
        if(b){
            return R.fail("用户名或者密码不符合规则，请重新输入");
        }
        return userService.login(userLoginParam);
    }
}
