package org.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.param.AddressListParam;
import org.example.pojo.Address;
import org.example.user.mapper.AddressMapper;
import org.example.user.service.AddressService;
import org.example.utils.R;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 10:06
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;
    /**  查询所有地址信息
     * @param addressListParam  封装了用户id 已经校验
     * @return  001 + 地址集合 / 004
     */
    @Override
    public R list(AddressListParam addressListParam) {
        //根据用户id进行查询
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", addressListParam.getUserId());
        List<Address> list = addressMapper.selectList(queryWrapper);

        log.info("AddressServiceImpl.list业务结束，结果:{}","查询所有地址信息");
        return R.ok(list);
    }

    /** 添加地址，并且查询最新的地址
     * @param address
     * @return
     */
    @Override
    public R save(Address address) {
        int rows = addressMapper.insert(address);
        if(rows == 0){
            log.info("AddressServiceImpl.save业务结束，结果:{}","保存失败");
            return R.fail("保存失败");
        }
        AddressListParam addressListParam = new AddressListParam();
        addressListParam.setUserId(address.getUserId());
        log.info("AddressServiceImpl.save业务结束，结果:{}","保存成功");
        return list(addressListParam);
    }

    /** 根据id删除地址信息
     * @param id
     * @return
     */
    @Override
    public R remove(Long id) {
        int rows = addressMapper.deleteById(id);
        if(rows == 0){
            log.info("AddressServiceImpl.remove业务结束，结果:{}","删除失败");
            return R.fail("删除失败");
        }
        return R.ok("删除成功");
    }
}
