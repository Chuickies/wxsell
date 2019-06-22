package com.zcj.service;

import com.zcj.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderMasterService {
    /**
     * 查询单个订单信息
     *
     * @return
     */
    OrderMasterDTO findOne(String orderId);

    /**
     * 创建订单
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO createOrder(OrderMasterDTO orderMasterDTO);

    /**
     * 通过openId 查询出订单列表并且分页
     * @param openId
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> findOrderListByOpenId(String openId, Pageable pageable);

    /**
     * 取消订单
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);

    /**
     * 完成订单
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO finish(OrderMasterDTO orderMasterDTO);

    /**
     * 支付订单
     * @param orderMasterDTO
     * @return
     */
    OrderMasterDTO paid(OrderMasterDTO orderMasterDTO);

    /**
     * 查询订单列表分页
     * @param pageable
     * @return
     */
    Page<OrderMasterDTO> findList(Pageable pageable);
}
