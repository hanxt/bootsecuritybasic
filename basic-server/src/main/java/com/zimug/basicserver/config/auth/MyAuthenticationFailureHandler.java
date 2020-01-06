package com.zimug.basicserver.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zimug.basicserver.config.exception.AjaxResponse;
import com.zimug.basicserver.config.exception.CustomException;
import com.zimug.basicserver.config.exception.CustomExceptionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    private static ObjectMapper objectMapper = new ObjectMapper();

    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String errorMsg = "用户名或者密码输入错误!";
        if(exception instanceof SessionAuthenticationException){
            errorMsg = exception.getMessage();
        }

        if(loginType.equalsIgnoreCase("JSON")){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    AjaxResponse.error(new CustomException(
                            CustomExceptionType.USER_INPUT_ERROR,
                            errorMsg))
            ));
        }else{
            //跳转到登陆页面
            super.onAuthenticationFailure(request,response,exception);
        }

    }
}
