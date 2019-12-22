package com.zimug.basicserver.config;

import com.zimug.basicserver.config.auth.MyAuthenticationFailureHandler;
import com.zimug.basicserver.config.auth.MyAuthenticationSuccessHandler;
import com.zimug.basicserver.config.auth.MyExpiredSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Resource
    MyAuthenticationSuccessHandler mySuthenticationSuccessHandler;

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
             .formLogin()
                .loginPage("/login.html")
                .usernameParameter("uname")
                .passwordParameter("pword")
                .loginProcessingUrl("/login")
                //.defaultSuccessUrl("/index")
                //.failureUrl("/login.html")
                .successHandler(mySuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
             .and()
             .authorizeRequests()
                .antMatchers("/login.html","/login").permitAll()
                .antMatchers("/biz1","/biz2") //需要对外暴露的资源路径
                    .hasAnyAuthority("ROLE_user","ROLE_admin")  //user角色和admin角色都可以访问
                //.antMatchers("/syslog","/sysuser")
                    //.hasAnyRole("admin")  //admin角色可以访问
                    //.hasAnyAuthority("ROLE_admin")
                .antMatchers("/syslog").hasAuthority("sys:log")
                .antMatchers("/sysuser").hasAuthority("sys:user")
                .anyRequest().authenticated()
             .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login.html")
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredSessionStrategy(new MyExpiredSessionStrategy());

    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
                    .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .authorities("sys:log","sys:user")
                //.roles("admin")
                    .and()
                .passwordEncoder(passwordEncoder());//配置BCrypt加密
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        //将项目中静态资源路径开放出来
        web.ignoring()
           .antMatchers( "/css/**", "/fonts/**", "/img/**", "/js/**");
    }

}
