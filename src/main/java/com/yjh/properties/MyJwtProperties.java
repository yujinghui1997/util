package com.yjh.properties;

import com.yjh.util.MyContact;
import com.yjh.util.MyJwtUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.yjh.jwt")
public class MyJwtProperties {

    private String headName = "username";
    private String headToken = "token";
    private String key = MyContact.JWTKEY;

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadToken() {
        return headToken;
    }

    public void setHeadToken(String headToken) {
        this.headToken = headToken;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
