package org.example.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.cart.mapper.CartMapper;
import org.example.cart.service.CartService;
import org.example.clients.ProductClient;
import org.example.param.CartParam;
import org.example.param.ProductDetailParam;
import org.example.pojo.Cart;
import org.example.pojo.Product;
import org.example.utils.R;
import org.example.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/21 16:43
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Autowired
    private ProductClient productClient;

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R save(CartParam cartParam) {
        //查询购物车对象
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, cartParam.getUserId())
                .eq(Cart::getProductId, cartParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        //根据商品id查询商品的详细信息
        ProductDetailParam productDetailParam = new ProductDetailParam();
        productDetailParam.setProductID(cartParam.getProductId());
        R r = productClient.detail(productDetailParam);
        if (!r.getCode().equals(R.SUCCESS_CODE)) {
            log.info("CartServiceImpl.save业务结束，结果:{}", "添加购物车失败");
            return R.fail("添加购物车失败");
        }
        //linkedHashMap类型 data
        Object data = r.getData();
        String jsonString = JSON.toJSONString(data);
        Product product = JSON.parseObject(jsonString, Product.class);
        if(product == null){
            log.info("CartServiceImpl.save业务结束，结果:{}", "添加购物车失败，商品已被删除");
            return R.fail("添加购物车失败，商品已被删除");
        }
        if (cart != null) {
            //购物车已经添加
            //判断购物车的数量和限购数量
            if (cart.getNum() >= product.getProductNum()) {
                log.info("CartServiceImpl.save业务结束，结果:{}", "商品已达限购数量");
                return R.ok(R.SUCCESS_CART_EXCEED_CODE, "商品已达限购数量");
            } else {
                log.info("CartServiceImpl.save业务结束，结果:{}", "该商品已在购物车，数量 +1");
                cart.setNum(cart.getNum() + 1);
                cartMapper.updateById(cart);
                return R.ok(R.SUCCESS_CART_REPEAT_CODE, "该商品已在购物车，数量 +1");
            }
        }
        //添加到购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartParam.getUserId());
        cart.setProductId(cartParam.getProductId());
        int rows = cartMapper.insert(cart);
        if (rows == 0) {
            log.info("CartServiceImpl.save业务结束，结果:{}", "添加购物车失败");
            return R.fail("添加购物车失败");
        }
        CartVo cartVo = new CartVo(product, cart);
        log.info("CartServiceImpl.save业务结束，结果:{}", cartVo);
        return R.ok("添加购物车成功", cartVo);
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        //根据用户id查询所有购物车
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(cartList)) {
            //购物车数据为null,返回空集合
            log.info("CartServiceImpl.list业务结束，结果:{}", "查询成功");
            return R.ok(new ArrayList<>());
        }
        //封装商品id集合去商品服务查询商品的信息
        List<Object> idList = cartList.stream().map(Cart::getProductId).collect(Collectors.toList());
        R r = productClient.byProductIds(idList);
        if (!r.getCode().equals(R.SUCCESS_CODE)) {
            log.info("CartServiceImpl.list业务结束，结果:{}", "查询购物车失败");
            return R.fail("查询购物车失败");
        }
        //将list转换成map,因为俩个list的顺序不一定一致
        List<LinkedHashMap> productLinkedHashMapList = (List<LinkedHashMap>) r.getData();
        ArrayList<Product> productList = new ArrayList<>(productLinkedHashMapList.size());
        for (LinkedHashMap linkedHashMap : productLinkedHashMapList) {
            Product product = JSON.parseObject(JSON.toJSONString(linkedHashMap), Product.class);
            productList.add(product);
        }
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //封装CartVoList
        ArrayList<CartVo> cartVoList = new ArrayList<>(cartList.size());
        for (int i = 0; i < cartList.size(); i++) {
            //将linkedHashMap类型先转为json字符串类型再转为实体类类型
            Cart cart = cartList.get(i);
            Product product = productMap.get(cart.getProductId());
            CartVo cartVo = new CartVo(product,cart);
            cartVoList.add(cartVo);
        }
        log.info("CartServiceImpl.list业务结束，结果:{}",cartVoList);
        //组装结果
        return R.ok("查询成功", cartVoList);
    }

    /**
     * @param cartParam
     * @return
     */
    @Override
    public R update(CartParam cartParam) {
        //查询修改的数量是否超过库存
        ProductDetailParam productDetailParam = new ProductDetailParam();
        productDetailParam.setProductID(cartParam.getProductId());
        R r = productClient.detail(productDetailParam);
        if(r.getCode().equals(R.FAIL_CODE)){
            log.info("CartServiceImpl.update业务结束，结果:{}","修改失败");
            return R.fail("修改失败");
        }
        //linkedHashMap类型,原型是Product类型
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap)r.getData();
        Object num = linkedHashMap.get("product_num"); //商品库存数量
        //超过，返回
        if(cartParam.getNum() > (Integer) num){
            log.info("CartServiceImpl.update业务结束，结果:{}","修改失败，不能超过商品库存");
            return R.fail("修改失败，不能超过商品库存");
        }
        //未超过，修改
        Cart cart = new Cart();
        cart.setNum(cartParam.getNum());
        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Cart::getUserId,cartParam.getUserId())
                .eq(Cart::getProductId,cartParam.getProductId());
        cartMapper.update(cart,updateWrapper);
        log.info("CartServiceImpl.update业务结束，结果:{}","修改成功");
        return R.ok("修改购物车数量成功");
    }
    /**
     * @param cartParam
     * @return
     */
    @Override
    public R delete(CartParam cartParam) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId,cartParam.getUserId())
                .eq(Cart::getProductId,cartParam.getProductId());
        int rows = cartMapper.delete(queryWrapper);
        if(rows == 0){
            log.info("CartServiceImpl.delete业务结束，结果:{}","删除购物车失败");
            return R.fail("删除购物车失败");
        }
        log.info("CartServiceImpl.delete业务结束，结果:{}","删除购物车成功");
        return R.ok("删除购物车成功");
    }

    /**
     * @param idList
     * @return
     */
    @Override
    public Integer deleteByIds(List<Integer> idList) {
        return cartMapper.deleteBatchIds(idList);
    }
}
