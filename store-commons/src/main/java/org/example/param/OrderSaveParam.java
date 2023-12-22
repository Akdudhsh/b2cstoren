package org.example.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.utils.R;
import org.example.vo.CartVo;

import java.util.List;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/22 15:01
 * @Description: 接受订单保存的参数的实体类
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSaveParam{
    @JsonProperty("user_id")
    private Integer userId;
    private List<CartVo> products;

}
