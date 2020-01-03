package com.zimug.basicserver.config;

import com.zimug.basicserver.config.auth.MyAuthenticationFailureHandler;
import com.zimug.basicserver.config.auth.MyAuthenticationSuccessHandler;
import com.zimug.basicserver.config.auth.MyExpiredSessionStrategy;
import com.zimug.basicserver.config.auth.MyUserDetailsService;
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

    @Resource
    MyUserDetailsService myUserDetailsService;

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
                .antMatchers("/index").authenticated()
                .anyRequest().access("@rabcService.hasPermission(request,authentication)")
                /*.antMatchers("/biz1","/biz2") //需要对外暴露的资源路径
                    .hasAnyAuthority("ROLE_user","ROLE_admin")  //user角色和admin角色都可以访问
                //.antMatchers("/syslog","/sysuser")
                //.hasAnyRole("admin")  //admin角色可以访问
                //.hasAnyAuthority("ROLE_admin")
                .antMatchers("/syslog").hasAuthority("/syslog")
                .antMatchers("/sysuser").hasAuthority("/sysuser")
                .anyRequest().authenticated()*/
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
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
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
