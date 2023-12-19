package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/19 16:31
 * @Description:
 * @Version 1.0
 */
@Data
public class Carousel implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @JsonProperty("carouse_id")
    private Integer carouselId;
    private String imgPath;
    private String describes;
    @JsonProperty("product_id")
    private Integer productId;
    private Integer priority;
}
