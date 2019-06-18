package com.zcj.service.impl;

import com.zcj.domain.ProductCategory;
import com.zcj.repository.ProductCategoryRepository;
import com.zcj.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository categoryRepository;
    @Override
    public ProductCategory findOne(Integer categoryId) {
        return null;
    }
    @Override
    public List<ProductCategory> findAll() {
        return null;
    }
    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return null;
    }
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
