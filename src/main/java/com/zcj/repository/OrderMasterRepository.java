package com.zcj.repository;

import com.sun.javafx.tk.quantum.MasterTimer;
import com.zcj.domain.OrderMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>, JpaSpecificationExecutor<OrderMaster> {
    List<OrderMaster> findByBuyerOpenid (String openid, Pageable pageable);
}
