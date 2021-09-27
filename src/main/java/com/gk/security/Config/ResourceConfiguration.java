package com.gk.security.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author guokui
 * @class srs
 * @date 2021/9/27 16:09
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers().antMatchers("/userInfo")
                .and()
                .authorizeRequests()
                .antMatchers("/userInfo").authenticated();  //受保护资源url: /userInfo 需要认证
    }
}