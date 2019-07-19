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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        orderMasterDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
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
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetails(orderDetailList);
        return orderMasterDTO;
    }


    @Override
    public Page<OrderMasterDTO> findOrderListByOpenId(String openId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(openId, pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        //转换不同的类型的List
        List<OrderMasterDTO> masterDTOList = OrderMaster2OrderMasterDTOConverter.convert(orderMasterList);
        return new PageImpl<>(masterDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional //加入事物
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //1、查询订单的状态
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {  //订单状态异常
            log.error("【取消订单】订单状态异常orderId={},orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2、更新数据订单的状态
        orderMasterDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateMaster = orderMasterRepository.save(orderMaster);
        if (updateMaster == null) {
            log.error("【取消订单】订单状更新失败 result={}", updateMaster);
            throw new SellException(ResultEnum.UPDATE_ORDER_STATUS_FAIL);
        }
        //4、还原库存
        List<OrderDetail> orderDetails = orderMasterDTO.getOrderDetails();
        if (CollectionUtils.isEmpty(orderDetails)) {
            log.error("【取消订单】订单中没有商品详情 orderDto={}", orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDtoList = orderDetails.stream().
                map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        //3、如果订单已经支付了则退款
        if(orderMasterDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){ //已支付
            //TODO
        }
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        //判断订单的状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderMasterDTO.getOrderStatus())){ //不是新订单
            log.error("【完成订单】订单状态异常orderId={},orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //更新订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        OrderMaster beUpdate = orderMasterRepository.save(orderMaster);
        if (beUpdate == null) {
            log.error("【完成订单】订单状更新失败 result={}", beUpdate);
            throw new SellException(ResultEnum.UPDATE_ORDER_STATUS_FAIL);
        }
        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {
        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderMasterDTO.getOrderStatus())){ //不是新订单
            log.error("【支付订单】订单状态异常orderId={},orderStatus={}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!PayStatusEnum.WAITING.getCode().equals(orderMasterDTO.getPayStatus())){
            log.error("【支付订单】订单状态异常orderId={},orderStatus={}",orderMasterDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //更新支付状态
        //更新订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderMasterDTO,orderMaster);
        OrderMaster beUpdate = orderMasterRepository.save(orderMaster);
        if (beUpdate == null) {
            log.error("【支付订单】订单支付状态更新失败 result={}", beUpdate);
            throw new SellException(ResultEnum.UPDATE_ORDER_STATUS_FAIL);
        }
        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findList(Pageable pageable) {
        return null;
    }
}
