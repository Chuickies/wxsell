package com.zcj.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {
    /*买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;
    /*买家手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;
    /*买家地址 */
    @NotEmpty(message = "地址必填")
    private String address;
    /*买家的微信openid */
    @NotEmpty(message = "买家的微信openId")
    private String openId;
    /*购物车 */
    @NotEmpty(message = "订单详情不能为空")
    private String Items;
}
