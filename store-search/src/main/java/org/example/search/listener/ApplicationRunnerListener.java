package org.example.search.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.example.clients.ProductClient;
import org.example.pojo.Product;
import org.example.pojo.ProductDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 13:06
 * @Description: 监听服务，服务启动后es同步mysql数据
 * @Version 1.0
 */
@Component
@Slf4j
public class ApplicationRunnerListener implements ApplicationRunner {
    @Autowired
    private RestHighLevelClient restClient;
    @Autowired
    private ProductClient productClient;
    private String createIndex = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"productId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productName\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"categoryId\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productTitle\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productIntro\":{\n" +
            "        \"type\":\"text\",\n" +
            "        \"analyzer\": \"ik_smart\",\n" +
            "        \"copy_to\": \"all\"\n" +
            "      },\n" +
            "      \"productPicture\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"productPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productSellingPrice\":{\n" +
            "        \"type\": \"double\"\n" +
            "      },\n" +
            "      \"productNum\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"productSales\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"all\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        //判断索引库是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        boolean exists = restClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        //存在，删除数据
        if(exists){
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("product");
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            restClient.deleteByQuery(deleteByQueryRequest,RequestOptions.DEFAULT);
        }else {
            //不存在，创建索引库
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(createIndex, XContentType.JSON);
            restClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }
        //从商品服务查询所有数据
        List<Product> list = productClient.list();
        //批量添加至索引库中
        BulkRequest bulkRequest = new BulkRequest();
        for (Product product : list) {
            ProductDoc productDoc = new ProductDoc(product);
            bulkRequest.add(new IndexRequest("product").id(productDoc.getProductId().toString())
                    .source(JSON.toJSONString(productDoc),XContentType.JSON));
        }

        restClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        log.info("ApplicationRunner.run业务结束，es数据初始化完成");
    }
}
