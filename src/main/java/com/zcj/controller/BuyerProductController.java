package com.zcj.controller;

import com.zcj.domain.ProductCategory;
import com.zcj.domain.ProductInfo;
import com.zcj.service.ProductCategoryService;
import com.zcj.service.ProductInfoService;
import com.zcj.vo.ProductInfoVo;
import com.zcj.vo.ProductVo;
import com.zcj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("list")
    public ResultVo findBuyerProductList() {
        //包装数据返回
        ResultVo resultVo = new ResultVo<>();
        //1 、查询所有上架的商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2、查询所有的类目信息 lambda 表达式的使用
        List<Integer> integers = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(integers);
        //3、数据的转换
        List<ProductVo> productVoList =new  ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setType(productCategory.getCategoryType());
            productVo.setName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                //比较类目类型是否相同
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    productInfoVo.setDescription(productInfo.getProductDescription());
                    productInfoVo.setId(productInfo.getProductId());
                    productInfoVo.setIcon(productInfo.getProductIcon());
                    productInfoVo.setName(productInfo.getProductName());
                    productInfoVo.setPrice(productInfo.getProductPrice());
                    // BeanUtils.copyProperties(productInfo,productInfoVo );
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setFoods(productInfoVoList);
            productVoList.add(productVo);
        }
        resultVo.setCode(0);
        resultVo.setMessage("成功");
        resultVo.setData(productVoList);
        return resultVo;
    }
}
