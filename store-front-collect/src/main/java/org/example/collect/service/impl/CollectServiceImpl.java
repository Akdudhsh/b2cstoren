package org.example.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.ProductClient;
import org.example.collect.mapper.CollectMapper;
import org.example.collect.service.CollectService;
import org.example.param.CollectListParam;
import org.example.param.CollectSaveParam;
import org.example.pojo.Collect;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 10:10
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;
    @Autowired
    private ProductClient productClient;

    /**
     * @param
     * @return
     */
    @Override
    public R save(CollectSaveParam collectSaveParam) {

        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId,collectSaveParam.getUserId())
                .eq(Collect::getProductId,collectSaveParam.getProductId());
        Collect collect = collectMapper.selectOne(queryWrapper);
        if(collect != null){
            //已经收藏
            log.info("CollectServiceImpl.save业务结束，结果:{}",collectSaveParam.getProductId() + "已被" + collectSaveParam.getUserId() + "收藏");
            return R.fail("该商品已经添加收藏，请到我的收藏查看");
        }
        collect = new Collect();
        collect.setUserId(collectSaveParam.getUserId());
        collect.setProductId(collectSaveParam.getProductId());
        collect.setCollectTime(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        int rows = collectMapper.insert(collect);
        if(rows == 0){
            log.info("CollectServiceImpl.save业务结束，结果:{}",collectSaveParam.getUserId() + "收藏" + collectSaveParam.getProductId() + "失败");
            return R.fail("收藏失败，请重试");
        }
        log.info("CollectServiceImpl.save业务结束，结果:{}",collectSaveParam.getUserId() + "收藏" + collectSaveParam.getProductId() + "成功");
        return R.ok("添加收藏成功");
    }

    /**
     * @param
     * @return
     */
    @Override
    //@Cacheable(value = "list.collect",key = "#userId")
    public R list(Integer userId) {
        //根据用户id所有收藏的商品id
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Collect::getProductId)
                .eq(Collect::getUserId,userId);
        List<Object> idList = collectMapper.selectObjs(queryWrapper);
        //根据用户收藏的商品id去商品服务查询对应的商品信息
        R r = productClient.byProductIds(idList);
        log.info("CollectServiceImpl.list业务结束，结果:{}",r.getData());
        return r;
    }

    /**
     * @param collectSaveParam
     * @return
     */
    @Override
    public R remove(CollectSaveParam collectSaveParam) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId,collectSaveParam.getUserId())
                .eq(Collect::getProductId,collectSaveParam.getProductId());
        int rows = collectMapper.delete(queryWrapper);
        if(rows == 0){
            log.info("CollectServiceImpl.remove业务结束，结果:{}",collectSaveParam.getUserId() + "删除收藏" + collectSaveParam.getProductId() + "失败");
            return R.ok("删除失败");
        }
        log.info("CollectServiceImpl.remove业务结束，结果:{}",collectSaveParam.getUserId() + "删除收藏" + collectSaveParam.getProductId() + "成功");
        return R.ok("删除成功");
    }
}
