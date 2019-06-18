package com.zcj.repository;

import com.zcj.domain.OrderMaster;
import com.zcj.utils.IdWorker;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    @Autowired
    private IdWorker idWorker;

    @Test
    public void saveOrderMaster() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(idWorker.nextId() + "");
        orderMaster.setBuyerName("超级无敌周");
        orderMaster.setBuyerPhone("13879106511");
        orderMaster.setBuyerAddress("深圳兴东站");
        orderMaster.setBuyerOpenid("21321421313");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        repository.save(orderMaster);
        Assert.assertNotNull(repository);
    }

    /**
     * 根据openId查询信息
     */
    @Test
    public void findByBuyerOpenid() {
        List<OrderMaster> masters = repository.findByBuyerOpenid("12343132132", PageRequest.of(1, 2));
        masters.stream().forEach((s)->{System.out.println(s);});
    }
}