package com.zcj.repository;

import com.zcj.domain.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    //保存类目
    @Test
    public void testSave(){
        ProductCategory pc = new ProductCategory();
        pc.setCategoryName("热榜");
        pc.setCategoryType(2);
        productCategoryRepository.save(pc);
    }
    //查找类目
    @Test
    @Transactional
    @Rollback(false)
    public void testFindProductCategory(){
        ProductCategory productCategory = productCategoryRepository.getOne(1);
        System.out.println(productCategory);
    }
    //更新类目表
    @Test
    @Transactional
    @Rollback(false)
    public void testUpdateProductCategory(){
        ProductCategory productCategory = productCategoryRepository.getOne(2);
        productCategory.setCategoryName("年度最热");
        productCategoryRepository.save(productCategory);
    }

    //通过categoryType 查询出类目信息
    @Test
    public void testFindByCategoryTypeIn(){
        List<Integer> list =  Arrays.asList(1,2,3);
        List<ProductCategory> categories = productCategoryRepository.findByCategoryTypeIn(list);
        if (categories != null&&categories.size()>0) {
            for (ProductCategory category : categories) {
                System.out.println(category);
            }
        }
    }
}