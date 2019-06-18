package com.zcj.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String message;
    /*返回的数据类型*/
    private T data;
}
