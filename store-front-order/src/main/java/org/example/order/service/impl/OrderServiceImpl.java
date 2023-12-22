package org.example.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.ProductClient;
import org.example.order.mapper.OrderMapper;
import org.example.order.service.OrderService;
import org.example.param.OrderSaveParam;
import org.example.pojo.Order;
import org.example.pojo.Product;
import org.example.utils.R;
import org.example.vo.CartVo;
import org.example.vo.OrderVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 15:13
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ProductClient productClient;

    /**
     * @param orderSaveParam
     * @return
     * todo:防止超卖、rabbitmq消息确认、分布式事务
     */
    @Override
    @Transactional
    public R saveOrders(OrderSaveParam orderSaveParam) {
        //解析参数封装订单对象
        List<CartVo> cartVoList = orderSaveParam.getProducts();
        if(CollectionUtils.isEmpty(cartVoList)){
            log.info("OrderServiceImpl.saveOrders业务结束，结果:{}","没有生成订单的购物车项");
            return R.fail("没有生成订单的购物车项");
        }
        ArrayList<Order> orderList = new ArrayList<>(cartVoList.size());
        ArrayList<Integer> cartIdList = new ArrayList<>(cartVoList.size());
        ArrayList<Product> productList = new ArrayList<>(cartVoList.size());
        //同一时间下单的所有订单的订单编号应该一致
        Long currentTimeMillis = System.currentTimeMillis();
        //保存订单对象
        for (CartVo cartVo : cartVoList) {
            Order order = new Order(null, currentTimeMillis, orderSaveParam.getUserId()
                    , cartVo.getProductID(), cartVo.getNum(), cartVo.getPrice(),
                    currentTimeMillis);
            orderList.add(order);
            cartIdList.add(cartVo.getId());
            productList.add(new Product(cartVo.getProductID(),cartVo.getNum()));
        }
        if (!saveBatch(orderList)) {
            log.info("OrderServiceImpl.saveOrders业务结束，结果:{}","生成订单失败");
            return R.fail("生成订单失败");
        }
        //异步通知购物车服务清空相应购物车项
        rabbitTemplate.convertAndSend("store.order","order.cart",cartIdList);
        //异步通知商品服务扣减库存，增加销量
        rabbitTemplate.convertAndSend("store.order","order.product",productList);
        //返回结果
        log.info("OrderServiceImpl.saveOrders业务结束，结果:{}","生成订单成功");
        return R.ok("购买成功");
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public R orderList(Integer userId) {
        //根据用户id查询所有的订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId,userId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(orderList)){
            log.info("OrderServiceImpl.orderList业务结束，结果:{}","查询订单成功");
            return R.ok(new ArrayList<>());
        }
        //去除订单中重复的商品id
        HashSet<Integer> productIdList = new HashSet<>();
        for (Order order : orderList) {
            productIdList.add(order.getProductId());
        }
        ArrayList<Object> idList = new ArrayList<>(productIdList);
        //查询商品的详情信息
        R r = productClient.byProductIds(idList);
        if(!r.getCode().equals(R.SUCCESS_CODE)){
            log.info("OrderServiceImpl.orderList业务结束，结果:{}","查询商品详细信息失败");
            return R.ok(new ArrayList<>());
        }
        // linkedHashmap -> product
        List<LinkedHashMap> productLinkedHashMapList = (List<LinkedHashMap>) r.getData();
        ArrayList<Product> productList = new ArrayList<>(productLinkedHashMapList.size());
        for (LinkedHashMap linkedHashMap : productLinkedHashMapList) {
            productList.add(JSON.parseObject(JSON.toJSONString(linkedHashMap),Product.class));
        }
        //商品List - 商品map
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //订单根据订单项进行分组
        Map<Long, List<Order>> listMap = orderList.stream().collect(Collectors.groupingBy(Order::getOrderId));
        //封装参数进行返回
        List<List<OrderVo>> orderVoList = new ArrayList<>();
        for (List<Order> value : listMap.values()) {
            List<OrderVo> orderVoInnerList = new ArrayList<>(value.size());
            for (Order order : value) {
                OrderVo orderVo = new OrderVo(order, productMap.get(order.getProductId()));
                orderVoInnerList.add(orderVo);
            }
            orderVoList.add(orderVoInnerList);
        }
        log.info("OrderServiceImpl.orderList业务结束，结果:{}","查询订单成功");
        return R.ok(orderVoList);
    }
}
