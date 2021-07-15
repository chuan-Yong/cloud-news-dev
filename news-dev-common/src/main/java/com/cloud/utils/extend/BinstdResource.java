package com.cloud.utils.extend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 99141
 */
@Component
@PropertySource("classpath:binstd.properties")
@ConfigurationProperties(prefix = "binstd")
public class BinstdResource {

    private String appKey;

    private String url;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
