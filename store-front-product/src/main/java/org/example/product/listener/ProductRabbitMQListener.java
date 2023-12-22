package org.example.product.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Product;
import org.example.product.service.ProductService;
import org.example.utils.R;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 16:05
 * @Description:
 * @Version 1.0
 */
@Component
@Slf4j
public class ProductRabbitMQListener {
    @Autowired
    private ProductService productService;
    /**
     * 该方法用于监听订单生成后修改商品的数量
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "product.updateNum.queue"),
            exchange = @Exchange(value = "store.order"),
            key = {"order.product"}
    ))
    public void updateProductNum(List<Product> productList){
        ArrayList<Integer> productIdList = new ArrayList<>(productList.size());
        for (Product product : productList) {
            productIdList.add(product.getProductId());
        }
        //根据商品id查询所有商品信息
        List<Product> productDetailList = (List<Product>) productService.byProductIds(productIdList).getData();
        //转成map
        Map<Integer, Product> productMap = productDetailList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //修改库存和销量
        for (Product product : productList) {
            Product product1 = productMap.get(product.getProductId());
            product1.setProductNum(product1.getProductNum() - product.getProductNum());
            product1.setProductSales(product1.getProductSales() + product.getProductNum());
        }
        List<Product> updateProductList = productMap.values().stream().collect(Collectors.toList());
        productService.updateBatchById(updateProductList);
    }
}
