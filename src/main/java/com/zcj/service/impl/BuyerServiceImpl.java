package com.zcj.service.impl;

import com.zcj.dto.OrderMasterDTO;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import com.zcj.service.BuyerService;
import com.zcj.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterService orderMasterService;
    @Override
    public OrderMasterDTO findOrderOne(String openid, String orderid) {
        OrderMasterDTO orderMasterDTO = checkedOwnerOrder(orderid, orderid);
        return orderMasterDTO;
    }



    @Override
    public OrderMasterDTO cancelOrder(String openid, String orderid) {
        OrderMasterDTO orderMasterDTO = checkedOwnerOrder(openid, orderid);
        if (orderMasterDTO == null) {
            log.error("【订单查询】 该订单不存在 orderMasterDTO={}" ,orderMasterDTO );
            throw new SellException(ResultEnum.ORDER_NO_EXIST);
        }
        return orderMasterService.cancel(orderMasterDTO);
    }

    /**
     * 判断订单是否属于该用户
     * @param openid
     * @param orderid
     * @return
     */
    private OrderMasterDTO checkedOwnerOrder(String openid, String orderid) {
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderid);
        if (orderMasterDTO == null) {
            return null;
        }
        //判断订单是否是自己的订单
        if(!orderMasterDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】 该订单属于该用户 openid={},orderid={}",orderMasterDTO.getBuyerOpenid(),orderMasterDTO.getOrderId() );
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderMasterDTO;
    }
}
