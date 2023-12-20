package org.example.search.listener;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.example.pojo.Product;
import org.example.pojo.ProductDoc;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 17:01
 * @Description:
 * @Version 1.0
 */
@Component
@Slf4j
public class RabbitMQListener {
    @Autowired
    private RestHighLevelClient restClient;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "store.insert.queue"),
            exchange = @Exchange(name = "store.topic",type = ExchangeTypes.DIRECT),
            key = {"product.insert"}))
    public void insert(Product product){
        try {
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            indexRequest.source(JSON.toJSONString(new ProductDoc(product)), XContentType.JSON);
            restClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("RabbitMQListener.insert业务结束");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "store.delete.queue"),
            exchange = @Exchange(name = "store.topic",type = ExchangeTypes.DIRECT),
            key = {"product.delete"}))
    public void delete(Integer productId){
        try {
            DeleteRequest deleteRequest = new DeleteRequest("product").id(productId.toString());
            restClient.delete(deleteRequest,RequestOptions.DEFAULT);
            log.info("RabbitMQListener.delete业务结束");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
