package com.zcj.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductVo {
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer type;
    @JsonProperty
    private List<ProductInfoVo> foods;

}
