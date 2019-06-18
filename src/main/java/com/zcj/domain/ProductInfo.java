package com.zcj.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "product_info")
public class ProductInfo {

    @Id
    private String productId;
    /*名称*/
    private String productName;
    /*价格*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*小图片*/
    private String productIcon;
    /*状态*/
    private Integer productStatus;
    /*类目型号*/
    private Integer categoryType;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;

}
