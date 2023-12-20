package org.example.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.example.param.SearchProductParam;
import org.example.pojo.ProductDoc;
import org.example.search.service.SearchService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/20 15:48
 * @Description:
 * @Version 1.0
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private RestHighLevelClient restClient;

    /**
     * @param searchProductParam
     * @return
     */
    @Override
    public R search(SearchProductParam searchProductParam) {
        SearchRequest searchRequest = new SearchRequest("product");
        searchRequest.source().query(QueryBuilders.matchQuery("all", searchProductParam.getSearch()))
                .from((searchProductParam.getCurrentPage() - 1) * searchProductParam.getPageSize())
                .size(searchProductParam.getPageSize());
        try {
            SearchResponse response = restClient.search(searchRequest, RequestOptions.DEFAULT);
            long total = response.getHits().getTotalHits().value;
            ArrayList arrayList = new ArrayList<>((int) total);
            SearchHit[] hits = response.getHits().getHits();
            if(hits.length != 0){
                for (SearchHit hit : hits) {
                    String source = hit.getSourceAsString();
                    ProductDoc productDoc = JSON.parseObject(source, ProductDoc.class);
                    arrayList.add(productDoc);
                }
            }
            log.info("SearchServiceImpl.search业务结束，结果:{}",hits);
            return R.ok("搜索成功",arrayList,total);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
