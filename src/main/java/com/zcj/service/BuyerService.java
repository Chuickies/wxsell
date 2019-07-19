package com.zcj.service;

import com.zcj.dto.OrderMasterDTO;

public interface BuyerService {
    /**
     * 通过 openid 和 orderId查询订单信息
     * @param openid
     * @param orderid
     * @return
     */
  OrderMasterDTO findOrderOne(String openid, String orderid);

    /**
     * 根据openid 和 orderid 取消订单
     * @param openid
     * @param orderid
     * @return
     */
  OrderMasterDTO cancelOrder(String openid,String orderid);
}
