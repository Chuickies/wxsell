package com.zcj.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Data  //lombok 生成了setter/getter ，toString 等方法
@Entity
@Table
@DynamicUpdate
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //类目Id
    private Integer categoryId;
    //类目名称
    private String categoryName;
    //类目类型
    private Integer categoryType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
