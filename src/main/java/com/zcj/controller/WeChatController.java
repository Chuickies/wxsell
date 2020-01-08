package com.zcj.controller;

import com.google.gson.Gson;
import com.zcj.domain.AccessToken;
import com.zcj.domain.WxJsapiSignature;
import com.zcj.enums.ResultEnum;
import com.zcj.exception.AesException;
import com.zcj.exception.SellException;
import com.zcj.utils.CheckUtil;
import com.zcj.utils.GenerateStrUtils;
import com.zcj.utils.GetJsSdkSignatureUtils;
import com.zcj.utils.SHA1;
import com.zcj.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/wechat")
@Slf4j
public class WeChatController {
    @Autowired
    private WxMpService wxMpService;

    @Value("${wechat.mpAppId}")
    private String appId;

    @Value("${wechat.mpAppSecret}")
    private String secrete;

    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("auth")
    public void getCodeAndToken(@RequestParam("code") String code) throws AesException {
        log.info("进入方法获取code......");
        log.info("code的值为{}",code);
        // 通过code 获取accessToken
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxcde50a847ef1d864&secret=2e242781221f13761b539a3392267a8a&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        Gson gson = new Gson();
        AccessToken accessToken = gson.fromJson(forObject, AccessToken.class);
        log.info("accessToken={}",accessToken);
        /*String urls="https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken.getAccess_token()+"&openid="+accessToken.getOpenid()+"&lang=zh_CN";
        RestTemplate restTemplates = new RestTemplate();
        String forObject1 = restTemplates.getForObject(urls, String.class);
        log.info("用户的基本信息:"+forObject1);*/
        // 获取jsapi_ticket
        String jsTicket="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken.getAccess_token()+"&type=jsapi";
        RestTemplate restTemplateJs = new RestTemplate();
        String jsTikectStr = restTemplateJs.getForObject(jsTicket, String.class);
        Long timeStamp=System.currentTimeMillis()/1000L;
        String noncestr = GenerateStrUtils.generateRandomStr(16);
        String signature = SHA1.genWithAmple(jsTikectStr, timeStamp + "", noncestr);
        log.info("signature={}",signature);
        log.info("jsTikectStr={}",jsTikectStr);
        log.info("timeStamp={}",timeStamp);
        log.info("noncestr={}",noncestr);
    }
    @GetMapping(value = "/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl ){
        //调用方法
        String url="http://zhoucj.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }
    @GetMapping(value = "/userInfo")
    public String userInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try{
           wxMpOAuth2AccessToken= wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e );
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        log.info("wxMpUser={}",wxMpUser);
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openId"+openId;
    }

    /**
     * 公众号测试号获取开发者权限
     * @param request
     * @param response
     * @param echostr
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    @GetMapping("/check")
    private Boolean getCheck(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(required = false) String echostr,
                            @RequestParam(required = false) String signature,
                            @RequestParam(required = false) String timestamp,
                            @RequestParam(required =false) String nonce) {

        final String token = "zcj19970303";//token是在验证的时候进行配置的
        try {
            //只需要把微信请求的 echostr, 返回给微信就可以了
            log.info("echostr ={}",echostr);
            log.info("signature={}",signature);
            log.info("timestamp={}",timestamp);
            log.info("nonce={}", nonce);
            log.info("-----------------------------------------");
            PrintWriter writer = response.getWriter();
            writer.print(echostr);
            writer.close();
            //String s = SHA1.getSHA1(token,timestamp,nonce,signature);
            return CheckUtil.checkSignature(token,signature,timestamp,nonce);
        } catch (Exception e) {
            log.info("测试微信公众号的接口配置信息发生异常：", e);
            return false;
        }
    }

    @GetMapping("/get/signature")
    public ResultVo GetWxJsapiSignature(@RequestParam String url){
        ResultVo resultVo = new ResultVo();
        WxJsapiSignature wxJsapiSignature = new WxJsapiSignature();
        String access_token = GetJsSdkSignatureUtils.getAccess_token(appId, secrete);
        log.info("access-token={}",access_token);
        //String ticket = (String) redisTemplate.opsForValue().get(appId + "_ticket");
        //if(StringUtils.isEmpty(ticket)){  // 缓存中没有票据，通过token获取票据
        //    String access_ticket = GetJsSdkSignatureUtils.getAccess_ticket(access_token);
        //    log.info("access_ticket={}",access_ticket);
        //    redisTemplate.opsForValue().set(appId + "_ticket",access_ticket);
        //}
        //String access_ticket = GetJsSdkSignatureUtils.getAccess_ticket(access_token);
        //log.info("access_ticket={}",access_ticket);
        String ticket="bxLdikRXVbTPdHSM05e5u1KzgEYU1Jm--3rld9GnlX8AFCQ6HANuK0b_u7TxNVQ8Ghfvpf_vouEvL23FKeUUKg";
        Long timeStamp=System.currentTimeMillis()/1000L;
        String noncestr = GenerateStrUtils.generateRandomStr(16);
        String signature = SHA1.genWithAmple(ticket, timeStamp + "", noncestr,url);
        wxJsapiSignature.setAppId(appId);
        wxJsapiSignature.setNonceStr(noncestr);
        wxJsapiSignature.setSignature(signature);
        wxJsapiSignature.setTimestamp(timeStamp);
        wxJsapiSignature.setUrl(url);
        log.info("wxJsapiSignature={}",wxJsapiSignature);
        resultVo.setData(wxJsapiSignature);
        resultVo.setCode(0);
        resultVo.setMessage("获取数据成功！！！");
        return resultVo;
    }


}