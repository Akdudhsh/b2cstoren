package org.example.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.CategoryClient;
import org.example.clients.SearchClient;
import org.example.param.ProductByCategoryParam;
import org.example.param.SearchProductParam;
import org.example.pojo.Picture;
import org.example.pojo.Product;
import org.example.product.mapper.PictureMapper;
import org.example.product.mapper.ProductMapper;
import org.example.product.service.ProductService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 19:55
 * @Description:
 * @Version 1.0
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {


    @Resource
    private ProductMapper productMapper;
    @Resource
    private PictureMapper pictureMapper;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SearchClient searchClient;

    /**  根据商品类别名称查询热门的7条商品
     * @param categoryName
     * @return
     */
    @Override
    @Cacheable(value = "list.product",key = "#categoryName")
    public R promo(String categoryName) {

        //调用类别服务返回商品id
        R r = categoryClient.getIdByName(categoryName);
        //根据商品id查询商品数据
        if(r.getData() == null){
            return r;
        }
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getCategoryId,r.getData())
                .orderByDesc(Product::getProductSales);
        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page, queryWrapper);
        List<Product> list = page.getRecords();

        //返回结果
        log.info("ProductServiceImpl.promo业务结束，结果:{}",list);
        return R.ok("查询成功",list);
    }
    /**
     * @param categoryName
     * @return
     */
    @Override
    @Cacheable(value = "list.product",key = "#categoryName")
    public R hots(List<String> categoryName) {
        ArrayList<Integer> idList = new ArrayList<>(categoryName.size());
        for (String s : categoryName) {
            R r = categoryClient.getIdByName(s);
            if(r.getData() != null)
                idList.add((Integer) r.getData());
        }
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        if(CollectionUtils.isEmpty(idList)){
            log.info("ProductServiceImpl.hots业务结束，结果:{}","无热门商品");
            return R.ok("无热门商品");
        }
        queryWrapper.in(Product::getCategoryId,idList)
                .orderByDesc(Product::getProductSales);
        IPage<Product> page = new Page<>(1,7);
        page = productMapper.selectPage(page,queryWrapper);
        List<Product> list = page.getRecords();
        log.info("ProductServiceImpl.hots业务结束，结果:{}",list);
        return R.ok("查询成功",list);
    }

    /** 查询所有类别的集合
     * @return
     */
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public R categoryList() {
        R r = categoryClient.list();
        log.info("ProductServiceImpl.categoryList业务结束，结果:{}",r.getData());
        return R.ok(r.getData());
    }

    /**
     * @param productByCategoryParam
     * @return
     */
    @Override
    @Cacheable(
            value = "list.product",
            key = "#productByCategoryParam.categoryID" +
                    "+'-'+#productByCategoryParam.currentPage" +
                    "+'-'+#productByCategoryParam.pageSize"
    )
    public R byCategory(ProductByCategoryParam productByCategoryParam) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        if(productByCategoryParam.getCategoryID().length != 0){
            queryWrapper.in(Product::getCategoryId,productByCategoryParam.getCategoryID());
        }
        IPage<Product> page = new Page<>(productByCategoryParam.getCurrentPage(), productByCategoryParam.getPageSize());
        page = productMapper.selectPage(page,queryWrapper);
        log.info("ProductServiceImpl.byCategory业务结束，结果:{}",page);
        return R.ok("查询成功",page.getRecords(),page.getTotal());
    }

    /**
     * @param productByCategoryParam
     * @return
     */
    @Override
    @Cacheable(
            value = "list.product",
            key = "#productByCategoryParam.currentPage" +
                    "+'-'+#productByCategoryParam.pageSize"
    )
    public R all(ProductByCategoryParam productByCategoryParam) {
        return byCategory(productByCategoryParam);
    }

    /**
     * @param productID
     * @return
     */
    @Cacheable(value = "product",key = "#productID")
    @Override
    public R detail(Integer productID) {

        Product product = productMapper.selectById(productID);
        log.info("ProductServiceImpl.detail业务结束，结果:{}",product);
        return R.ok(product);
    }

    /**
     * @param productID
     * @return
     */
    @Override
    @Cacheable(value = "picture",key = "#productID",cacheManager = "cacheManagerDay")
    public R pictures(Integer productID) {
        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Picture::getProductId,productID);
        List<Picture> list = pictureMapper.selectList(queryWrapper);
        log.info("ProductServiceImpl.detail业务结束，结果:{}",list);
        return R.ok(list);
    }

    /** 查询所有商品数据，供es索引库的初始化
     * @return
     */
    @Override
    public List<Product> list() {
        log.info("ProductServiceImpl.list业务结束");
        return productMapper.selectList(null);
    }

    /** 搜索商品
     * @param searchProductParam
     * @return
     */
    @Override
    public R search(SearchProductParam searchProductParam) {
        log.info("ProductServiceImpl.search业务结束");
        return searchClient.search(searchProductParam);
    }
}
