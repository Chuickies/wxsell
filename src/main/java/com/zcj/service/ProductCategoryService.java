package com.zcj.service;

import com.zcj.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 通过Id查询出类目的信息
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有的类目信息
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 保存类目信息
     */
    ProductCategory save(ProductCategory productCategory);

    /**
     * 根据类目的类型查询出类目的信息
     * @param categoryTypeList 类目的类型Id
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
