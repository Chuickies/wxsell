package com.zcj.repository;

import com.sun.javafx.tk.quantum.MasterTimer;
import com.zcj.domain.OrderMaster;
import com.zcj.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>, JpaSpecificationExecutor<OrderMaster> {
    Page<OrderMaster> findByBuyerOpenid (String openid, Pageable pageable);
}
