package com.gk.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author guokui
 * @class srs
 * @date 2021/9/27 16:09
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    //配置客户端详情（Client Details）
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client_id")  //客户端id
                /**
                 * 授权模式：
                 * 授权码模式（authorization_code)
                 * 简化模式（implicit)
                 * 密码模式（resource owner password credentials）
                 * 客户端模式（client credentials）
                 */
                .authorizedGrantTypes("client_credentials", "refresh_token", "authorization_code") //授权类型
                .redirectUris("https://www.baidu.com")
                .scopes("all") //授权范围
                .secret(passwordEncoder.encode("123456"));//密钥
    }



}
