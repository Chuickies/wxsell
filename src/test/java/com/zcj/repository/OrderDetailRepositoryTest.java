
package com.zcj.repository;

import com.zcj.domain.OrderDetail;
import com.zcj.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加订单详情
     */
    @Test
    public void saveOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId(idWorker.nextId() + "");
        orderDetail.setOrderId("1140240881715019776");
        orderDetail.setProductId("1234");
        orderDetail.setProductPrice(new BigDecimal(2.4));
        orderDetail.setProductIcon("http://yyyyyy.jpg");
        orderDetail.setProductName("白粥");
        orderDetail.setProductQuantity(3);
        repository.save(orderDetail);

    }

    /**
     * 根据OrderId查询出信息
     */
    @Test
    public void findByOrAndOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("1140240881715019776");
        orderDetailList.stream().forEach((s)->{System.out.println(s);});
    }
}