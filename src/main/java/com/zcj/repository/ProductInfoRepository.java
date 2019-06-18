package com.zcj.repository;

import com.zcj.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>, JpaSpecificationExecutor<ProductInfo> {
    /**
     * 根据状态查询产品信息
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
