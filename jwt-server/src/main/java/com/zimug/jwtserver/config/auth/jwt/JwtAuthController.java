package com.zimug.jwtserver.config.auth.jwt;

import com.zimug.jwtserver.config.exception.AjaxResponse;
import com.zimug.jwtserver.config.exception.CustomException;
import com.zimug.jwtserver.config.exception.CustomExceptionType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class JwtAuthController {


    @Resource
    JwtAuthService jwtAuthService;

    @RequestMapping(value = "/authentication")
    public AjaxResponse login(@RequestBody Map<String,String> map){
        String username  = map.get("username");
        String password = map.get("password");

        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return AjaxResponse.error(
                    new CustomException(CustomExceptionType.USER_INPUT_ERROR,
                    "用户名或者密码不能为空"));
        }
        try {
            return AjaxResponse.success(jwtAuthService.login(username, password));
        }catch (CustomException e){
            return AjaxResponse.error(e);
        }
    }

    @RequestMapping(value = "/refreshtoken")
    public  AjaxResponse refresh(@RequestHeader("${jwt.header}") String token){
            return AjaxResponse.success(jwtAuthService.refreshToken(token));
    }

}
