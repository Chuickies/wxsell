package com.zcj.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zcj.domain.OrderDetail;
import com.zcj.dto.OrderMasterDTO;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import com.zcj.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderForm2OrderMasterDTOConvert {
    public static OrderMasterDTO convert(OrderForm orderForm) {
        //json字符串转换器
        Gson gson = new Gson();
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerName(orderForm.getName());
        orderMasterDTO.setBuyerPhone(orderForm.getPhone());
        orderMasterDTO.setBuyerAddress(orderForm.getAddress());
        orderMasterDTO.setBuyerOpenid(orderForm.getOpenId());
        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            log.error("【对象转换】错误 string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderMasterDTO.setOrderDetails(orderDetailList);
        return orderMasterDTO;
    }
}
