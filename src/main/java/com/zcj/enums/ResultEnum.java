package com.zcj.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NO_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存出错"),
    ORDER_NO_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    UPDATE_ORDER_STATUS_FAIL(15,"订单状态更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单商品详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态异常");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
