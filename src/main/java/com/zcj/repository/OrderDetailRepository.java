package com.zcj.repository;

import com.zcj.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String>, JpaSpecificationExecutor<OrderDetail> {
    List<OrderDetail> findByOrAndOrderId(String orderId);
}
