package org.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.UserConstant;
import org.example.param.UserCheckParam;
import org.example.param.UserLoginParam;
import org.example.pojo.User;
import org.example.user.mapper.UserMapper;
import org.example.user.service.UserService;
import org.example.utils.MD5Util;
import org.example.utils.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 18:14
 * @Description:
 * @Version 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;


    /**  查询用户名在数据库表中是否存在
     * @param userCheckParam  封装用户名的实体 参数已校验
     * @return 001 004
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        //查询结果
        Boolean isUsed = getUserNameIsUsed(userCheckParam.getUserName());
        //处理查询结果
        if(!isUsed){
            log.info("UserServiceImpl.check业务结束，结果:{}","账号可用");
            return R.ok("用户名可用");
        }
        log.info("UserServiceImpl.check业务结束，结果:{}","账号不可用");
        return R.fail("用户名已被使用");
    }

    private Boolean getUserNameIsUsed(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        Long count = userMapper.selectCount(queryWrapper);
        return count == 1;
    }

    /** 进行用户注册
     * @param user 封装了注册用户的参数的实体 参数已校验
     * @return 001 004
     */
    @Override
    public R register(User user) {
        //1.检查用户是否被注册
        Boolean isUsed = getUserNameIsUsed(user.getUserName());
        if(isUsed){
            log.info("UserServiceImpl.register业务结束，结果:{}","用户名已被注册");
            return R.fail("用户名已被使用");
        }
        //2.对密码进行MD5加密 并进行加盐处理
        String newPwd = MD5Util.encode(user.getPassword() + UserConstant.PASSWORD_SALT);
        user.setPassword(newPwd);
        //3.插入数据（注册用户）
        int rows = userMapper.insert(user);
        //4.处理插入结果
        if(rows == 0){
            log.info("UserServiceImpl.register业务结束，结果:{}","注册失败");
            return R.fail("注册失败，请稍后重试");
        }
        log.info("UserServiceImpl.register业务结束，结果:{}","注册成功");
        return R.ok("注册成功");
    }

    /** 判断用户名和密码是否正确
     * @param userLoginParam  封装了用户名和密码参数的对象 已经校验了参数
     * @return 001 004
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        //1.处理密码
        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstant.PASSWORD_SALT);
        //2.查询用户和密码是否正常
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userLoginParam.getUserName())
                .eq(User::getPassword,newPwd);
        User user = userMapper.selectOne(queryWrapper);
        //3.处理查询结果
        if(user == null){
            log.info("UserServiceImpl.login业务结束，结果:{}","用户名或者密码不正确");
            return R.fail("用户名或者密码不正确");
        }
        //设空密码,电话，避免传回前端
        user.setPassword(null);
        user.setUserPhonenumber(null);
        log.info("UserServiceImpl.login业务结束，结果:{}","登录成功");
        return R.ok("登录成功",user);
    }
}
