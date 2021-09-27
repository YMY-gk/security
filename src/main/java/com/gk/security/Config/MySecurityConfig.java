package com.gk.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author guokui
 * @class security
 * @date 2021/9/2 16:47
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();//没有加密
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()  //所有端点配置
                .antMatchers("/oauth/**", "/login")  // 匹配一个数据 ant路径格式
                .and()
                .authorizeRequests()  // url权限配置
                .antMatchers("/login").permitAll()  // 表示登录表单页面不拦截
                .antMatchers("/oauth/**").authenticated()  // 保护url，需要用户登录
                .and()
                .formLogin().permitAll()  //没有自定义loginpage  则不要写上loginPage("/xxxx") 否则404
                .and()
                .logout().permitAll()
                // /logout退出清除cookie
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }

}
