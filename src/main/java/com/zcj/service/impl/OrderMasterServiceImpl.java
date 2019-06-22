package com.zcj.service.impl;

import com.zcj.converter.OrderMaster2OrderMasterDTOConverter;
import com.zcj.domain.OrderDetail;
import com.zcj.domain.OrderMaster;
import com.zcj.domain.ProductInfo;
import com.zcj.dto.CartDTO;
import com.zcj.dto.OrderMasterDTO;
import com.zcj.enums.OrderStatusEnum;
import com.zcj.enums.PayStatusEnum;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import com.zcj.repository.OrderDetailRepository;
import com.zcj.repository.OrderMasterRepository;
import com.zcj.service.OrderMasterService;
import com.zcj.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private IdWorker idWorker;

    @Override
    public OrderMasterDTO createOrder(OrderMasterDTO orderMasterDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = idWorker.nextId() + "";
        //1、查询商品
        List<OrderDetail> orderDetails = orderMasterDTO.getOrderDetails();
        if (orderDetails != null && orderDetails.size() > 0) {
            for (OrderDetail orderDetail : orderDetails) {
                ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
                if (productInfo == null) {  //没有这个商品信息 自定义异常信息
                    throw new SellException(ResultEnum.PRODUCT_NO_EXIST);
                }
                //2、计算订单总价  = 购买数量* 单价+ orderAmount
                orderAmount = productInfo.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
                //写入订单详情数据
                orderDetail.setDetailId(idWorker.nextId() + "");
                orderDetail.setOrderId(orderId);
                BeanUtils.copyProperties(productInfo, orderDetail);
                orderDetailRepository.save(orderDetail);

            }
        }
        //3、保存到订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAITING.getCode());
        orderMasterRepository.save(orderMaster);
        //4、扣除库存
        List<CartDTO> cartDTOList = orderMasterDTO.getOrderDetails()
                .stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO findOne(String orderId) {
        //查询单个订单信息
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NO_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster,orderMasterDTO);
        orderMasterDTO.setOrderDetails(orderDetailList);
        return orderMasterDTO;
    }


    @Override
    public Page<OrderMasterDTO> findOrderListByOpenId(String openId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openId,pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        //转换不同的类型的List
        List<OrderMasterDTO> masterDTOList = OrderMaster2OrderMasterDTOConverter.convert(orderMasterList);
        return new PageImpl<>(masterDTOList, pageable,orderMasterPage.getTotalElements());
    }

    @Override
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {
        return null;
    }

    @Override
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        return null;
    }

    @Override
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
        return null;
    }
}
