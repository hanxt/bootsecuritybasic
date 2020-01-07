package com.zimug.basicserver.config.auth.imagecode;

import java.time.LocalDateTime;

public class CaptchaCode {

    private String code;

    private LocalDateTime expireTime;


    public CaptchaCode(String code, int expireAfterSeconds){
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireAfterSeconds);
    }

    public boolean isExpired(){
        return  LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }
}
