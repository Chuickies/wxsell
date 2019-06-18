package com.zcj.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NO_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存出错");


    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
