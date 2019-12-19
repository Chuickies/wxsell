package com.zcj.domain;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
