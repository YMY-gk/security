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
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
//                .loginPage("/index") // 配置哪个 url 为登录页面
                .loginProcessingUrl("/user/login") // 设置哪个是登录的 url。
                .defaultSuccessUrl("/index").failureUrl("/")
                .permitAll() ;// 登录成功之后跳转到哪个 url
        http.authorizeRequests()
                .antMatchers("/index") //表示配置请求路径
                .permitAll() // 指定 URL 无需保护。
                .antMatchers("/role").hasAnyAuthority("admin")//表示配置请求路径
                .antMatchers("/user/role").hasAnyAuthority("user")//表示配置请求路径
                .antMatchers("/role1").hasAnyRole("role")//表示配置请求路径
                .antMatchers("/user/role1").hasAnyRole("role1")//表示配置请求路径
                .anyRequest() // 其他请求
                .authenticated(); //需要认证
// 关闭 csrf
        http.csrf().disable();
    }

}
