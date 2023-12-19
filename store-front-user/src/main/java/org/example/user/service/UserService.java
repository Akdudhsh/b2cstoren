package org.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.param.UserCheckParam;
import org.example.param.UserLoginParam;
import org.example.pojo.User;
import org.example.utils.R;

/**
 * @Author:罗蓉鑫
 * @Date: 2023/12/18 18:14
 * @Description:
 * @Version 1.0
 */
public interface UserService{

    R check(UserCheckParam userCheckParam);

    R register(User user);

    R login(UserLoginParam userLoginParam);
}
