package com.zcj.service;

import com.zcj.domain.ProductInfo;
import com.zcj.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    /**
     * 通过Id查询商品的信息
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有商品上架的信息
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 获取所有的商品信息
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 增加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减少库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
