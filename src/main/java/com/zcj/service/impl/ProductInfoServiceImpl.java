package com.zcj.service.impl;

import com.zcj.domain.ProductInfo;
import com.zcj.dto.CartDTO;
import com.zcj.enums.ProductStatusEnum;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import com.zcj.repository.ProductInfoRepository;
import com.zcj.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository infoRepository;

    @Override
    public ProductInfo findOne(String productId) {
        return infoRepository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return infoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {

        return infoRepository.findAll(PageRequest.of(1,1 ));
    }


    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return infoRepository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = infoRepository.findById(cartDTO.getProductId()).get();
            if (productInfo == null) { //商品不存在
                throw new SellException(ResultEnum.PRODUCT_NO_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            infoRepository.save(productInfo);
        }
    }
}
