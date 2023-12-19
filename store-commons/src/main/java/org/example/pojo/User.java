package org.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: 罗蓉鑫
 * @Date: 2023/12/18 16:42
 * @Description:
 * @Version 1.0
 */
@Data
public class User implements Serializable {
    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    @JsonProperty("user_id") //jackson的注解，用于属性格式化
    private  String userId;
    @Length(min = 6)
    private String userName;
    @NotBlank
    //@JsonIgnore //jackson的注解,不接受json数据，也不生成json数据
    @JsonInclude(JsonInclude.Include.NON_NULL) //jackson的注解,为null时不接受不生成，不为null时接受和生成
    private String password;
    @NotBlank
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPhonenumber;
}
