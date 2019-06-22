package com.zcj.service.impl;

import com.zcj.domain.OrderDetail;
import com.zcj.dto.OrderMasterDTO;
import com.zcj.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {
    @Autowired
    private OrderMasterService orderMasterService;
    private static final String BUYER_OPENID = "120120100";

    @Test
    public void createOrder() {
        //构造订单对象
        OrderMasterDTO orderMasterdto = new OrderMasterDTO();
        orderMasterdto.setBuyerName("史塔克");
        orderMasterdto.setBuyerAddress("江西先锋软件职业大学");
        orderMasterdto.setBuyerPhone("13879106513");
        orderMasterdto.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1234");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("1235");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderMasterdto.setOrderDetails(orderDetailList);
        //创建订单
        OrderMasterDTO result = orderMasterService.createOrder(orderMasterdto);
        log.info("【创建订单】result{}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderMasterDTO result = orderMasterService.findOne("1140884725372567552");
        log.info("【订单信息】result{}",result);
        Assert.assertNotNull(result);
    }


    @Test
    public void findList() {
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findOrderListByOpenId("12343132132", PageRequest.of(1, 1));
        log.info("【订单分页信息】result{}",orderMasterDTOPage.getContent(),orderMasterDTOPage.getTotalElements());
        Assert.assertNotNull(orderMasterDTOPage);
    }

    @Test
    public void findOrderListByOpenId() {
    }

    @Test
    public void cancel() {

    }

    @Test
    public void finish() {

    }

    @Test
    public void paid() {

    }
}