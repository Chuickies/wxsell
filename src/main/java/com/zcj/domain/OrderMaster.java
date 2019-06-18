package com.zcj.domain;

import com.zcj.enums.OrderStatusEnum;
import com.zcj.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "order_master")
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAITING.getCode();
    private Date CreateTime;
    private Date updateTime;
}
