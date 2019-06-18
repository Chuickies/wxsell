package com.zcj.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(0,"以上架"),
    DOWN(1,"以下架");

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }


}