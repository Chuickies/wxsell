package com.zcj.controller;

import com.zcj.converter.OrderForm2OrderMasterDTOConvert;
import com.zcj.dto.OrderMasterDTO;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import com.zcj.form.OrderForm;
import com.zcj.service.BuyerService;
import com.zcj.service.OrderMasterService;
import com.zcj.utils.ResultVOUtil;
import com.zcj.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     *
     * @return
     */
    @PostMapping(value = "/create")
    public ResultVo<Map<String, String>> createOrder(@Valid OrderForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {  //效验参数是否正确
            log.error("【创建订单】参数不正确, orderForm={}", form);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTOConvert.convert(form);
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetails())) {
            log.error("【创建订单】购物车不能为空", orderMasterDTO.getOrderDetails());
            throw new SellException(ResultEnum.CAR_EMPTY);
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderMasterDTO.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 根据openid分页查询出订单信息
     *
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/list")
    public ResultVo<List<OrderMasterDTO>> findList(@RequestParam("openid") String openid,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查出订单列表】", "openId不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findOrderListByOpenId(openid, PageRequest.of(page, size));

        return ResultVOUtil.success(orderMasterDTOPage.getContent());
    }

    @GetMapping(value = "detail")
    public ResultVo<OrderMasterDTO> findDetailList(@RequestParam("openid") String openid, @RequestParam("orderid") String orderid) {
        OrderMasterDTO orderMasterDTO = buyerService.findOrderOne(openid, orderid);
        return ResultVOUtil.success(orderMasterDTO);
    }

    @PostMapping(value = "cancel")
    public ResultVo cancel(@RequestParam("openid") String openid, @RequestParam("orderid") String orderid) {
        buyerService.cancelOrder(openid, orderid);
        return ResultVOUtil.success();
    }
}
