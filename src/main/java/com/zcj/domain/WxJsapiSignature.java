//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zcj.domain;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public class WxJsapiSignature implements Serializable {
    private static final long serialVersionUID = -1116808193154384804L;
    private String appId;
    private String nonceStr;
    private long timestamp;
    private String url;
    private String signature;

    public static WxJsapiSignature.WxJsapiSignatureBuilder builder() {
        return new WxJsapiSignature.WxJsapiSignatureBuilder();
    }

    public String getAppId() {
        return this.appId;
    }

    public String getNonceStr() {
        return this.nonceStr;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getUrl() {
        return this.url;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WxJsapiSignature)) {
            return false;
        } else {
            WxJsapiSignature other = (WxJsapiSignature)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    Object this$appId = this.getAppId();
                    Object other$appId = other.getAppId();
                    if (this$appId == null) {
                        if (other$appId == null) {
                            break label63;
                        }
                    } else if (this$appId.equals(other$appId)) {
                        break label63;
                    }

                    return false;
                }

                Object this$nonceStr = this.getNonceStr();
                Object other$nonceStr = other.getNonceStr();
                if (this$nonceStr == null) {
                    if (other$nonceStr != null) {
                        return false;
                    }
                } else if (!this$nonceStr.equals(other$nonceStr)) {
                    return false;
                }

                if (this.getTimestamp() != other.getTimestamp()) {
                    return false;
                } else {
                    Object this$url = this.getUrl();
                    Object other$url = other.getUrl();
                    if (this$url == null) {
                        if (other$url != null) {
                            return false;
                        }
                    } else if (!this$url.equals(other$url)) {
                        return false;
                    }

                    Object this$signature = this.getSignature();
                    Object other$signature = other.getSignature();
                    if (this$signature == null) {
                        if (other$signature != null) {
                            return false;
                        }
                    } else if (!this$signature.equals(other$signature)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WxJsapiSignature;
    }

    public int hashCode() {
        Boolean PRIME = true;
        int result = 1;
        Object $appId = this.getAppId();
        result = result * 59 + ($appId == null ? 43 : $appId.hashCode());
        Object $nonceStr = this.getNonceStr();
        result = result * 59 + ($nonceStr == null ? 43 : $nonceStr.hashCode());
        long $timestamp = this.getTimestamp();
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        Object $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        Object $signature = this.getSignature();
        result = result * 59 + ($signature == null ? 43 : $signature.hashCode());
        return result;
    }

    public String toString() {
        return "WxJsapiSignature(appId=" + this.getAppId() + ", nonceStr=" + this.getNonceStr() + ", timestamp=" + this.getTimestamp() + ", url=" + this.getUrl() + ", signature=" + this.getSignature() + ")";
    }

    public WxJsapiSignature() {
    }

    @ConstructorProperties({"appId", "nonceStr", "timestamp", "url", "signature"})
    public WxJsapiSignature(String appId, String nonceStr, long timestamp, String url, String signature) {
        this.appId = appId;
        this.nonceStr = nonceStr;
        this.timestamp = timestamp;
        this.url = url;
        this.signature = signature;
    }

    public static class WxJsapiSignatureBuilder {
        private String appId;
        private String nonceStr;
        private long timestamp;
        private String url;
        private String signature;

        WxJsapiSignatureBuilder() {
        }

        public WxJsapiSignature.WxJsapiSignatureBuilder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public WxJsapiSignature.WxJsapiSignatureBuilder nonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public WxJsapiSignature.WxJsapiSignatureBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public WxJsapiSignature.WxJsapiSignatureBuilder url(String url) {
            this.url = url;
            return this;
        }

        public WxJsapiSignature.WxJsapiSignatureBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public WxJsapiSignature build() {
            return new WxJsapiSignature(this.appId, this.nonceStr, this.timestamp, this.url, this.signature);
        }

        public String toString() {
            return "WxJsapiSignature.WxJsapiSignatureBuilder(appId=" + this.appId + ", nonceStr=" + this.nonceStr + ", timestamp=" + this.timestamp + ", url=" + this.url + ", signature=" + this.signature + ")";
        }
    }
}
