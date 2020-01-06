package com.zimug.basicserver.config.auth.imagecode;

import com.zimug.basicserver.config.auth.MyAuthenticationFailureHandler;
import com.zimug.basicserver.utils.MyContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Component
public class CaptchaCodeFilter extends OncePerRequestFilter {

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        if(StringUtils.equals("/login",request.getRequestURI())
                && StringUtils.equalsIgnoreCase(request.getMethod(),"post")){

            try{
                //验证谜底与用户输入是否匹配
                validate(new ServletWebRequest(request));
            }catch(AuthenticationException e){
                myAuthenticationFailureHandler.onAuthenticationFailure(
                        request,response,e
                );
                return;
            }

        }

        filterChain.doFilter(request,response);

    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        HttpSession session = request.getRequest().getSession();

        String codeInRequest = ServletRequestUtils.getStringParameter(
                request.getRequest(),"captchaCode");
        if(StringUtils.isEmpty(codeInRequest)){
            throw new SessionAuthenticationException("验证码不能为空");
        }

        // 3. 获取session池中的验证码谜底
        CaptchaImageVO codeInSession = (CaptchaImageVO)
                session.getAttribute(MyContants.CAPTCHA_SESSION_KEY);
        if(Objects.isNull(codeInSession)) {
            throw new SessionAuthenticationException("验证码不存在");
        }

        // 4. 校验服务器session池中的验证码是否过期
        if(codeInSession.isExpired()) {
            session.removeAttribute(MyContants.CAPTCHA_SESSION_KEY);
            throw new SessionAuthenticationException("验证码已经过期");
        }

        // 5. 请求验证码校验
        if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new SessionAuthenticationException("验证码不匹配");
        }

    }



}
