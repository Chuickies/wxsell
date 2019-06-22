package com.zcj.converter;

import com.zcj.domain.OrderMaster;
import com.zcj.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderMasterDTOConverter {
    public static OrderMasterDTO convert(OrderMaster orderMaster){
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster,orderMasterDTO);
        return orderMasterDTO;
    }
    public static List<OrderMasterDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
    }

}
