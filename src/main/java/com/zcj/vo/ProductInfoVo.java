package com.zcj.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVo {
    @JsonProperty
    private String Id;
    @JsonProperty
    private String name;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private String description;
    @JsonProperty
    private String icon;
}
