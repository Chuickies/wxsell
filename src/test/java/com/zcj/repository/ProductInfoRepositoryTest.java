package com.zcj.repository;

import com.zcj.domain.ProductInfo;
import com.zcj.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    /**
     * 保存
     */
    @Test
    public void save(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1235");
        productInfo.setProductName("白粥");
        productInfo.setCategoryType(2);
        productInfo.setProductPrice(new BigDecimal(2.5));
        productInfo.setProductStock(6);
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductIcon("http://xxxxxx.jpg");
        productInfo.setProductDescription("清晨的白粥，不如夜晚的酒");
        ProductInfo info = repository.save(productInfo);
        Assert.assertNotNull(info);
    }
    @Test
    public void findByProductStatus() {
        List<ProductInfo> productStatus = repository.findByProductStatus(0);
        for (ProductInfo status : productStatus) {
            System.out.println(status);
        }
    }
}