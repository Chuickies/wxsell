package com.zcj.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zcj.domain.OrderDetail;
import com.zcj.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderMasterDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;
    @JsonSerialize(using = Date2LongSerializer.class)  //解决时间显示页面是多出1000的问题
    private Date CreateTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    private List<OrderDetail> orderDetails;
}
