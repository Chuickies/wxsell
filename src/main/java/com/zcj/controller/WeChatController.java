package com.zcj.controller;

import com.zcj.enums.ResultEnum;
import com.zcj.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
@RequestMapping(value = "/wechat")
@Slf4j
public class WeChatController {
    @Autowired
    private WxMpService wxMpService;
    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl ){
        //调用方法
        String url="http://note.java.itcast.cn/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }
    @GetMapping(value = "userInfo")
    public String userInfo(@RequestParam("code") String code,@RequestParam("stata") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try{
           wxMpOAuth2AccessToken= wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e );
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openId"+openId;

    }
}
