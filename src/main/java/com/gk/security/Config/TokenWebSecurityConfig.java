package com.gk.security.Config;

import com.gk.security.filter.TokenAuthenticationFilter;
import com.gk.security.filter.TokenLoginFilter;
import com.gk.security.securityConfug.DefaultPasswordEncoder;
import com.gk.security.securityConfug.TokenLogoutHandler;
import com.gk.security.securityConfug.TokenManager;
import com.gk.security.securityConfug.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
    //自定义查询数据库用户名密码和权限信息
    private UserDetailsService userDetailsService;
    //token 管理工具类（生成 token）
    private TokenManager tokenManager;
    //密码管理工具类
    private DefaultPasswordEncoder defaultPasswordEncoder;
    //redis 操作工具类
    private RedisTemplate redisTemplate;
    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService,
                                  DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate
                                          redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }
    /**
     * 配置设置
     */
    //设置退出的地址和 token，redis 操作地址
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/admin/acl/index/logout")
                .addLogoutHandler(new
                        TokenLogoutHandler(tokenManager,redisTemplate)).and()
                .addFilter(new TokenLoginFilter(authenticationManager(),
                        tokenManager, redisTemplate))
                .addFilter(new
                        TokenAuthenticationFilter(authenticationManager(), tokenManager,
                        redisTemplate)).httpBasic();
    }
    /**
     * 密码处理
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {

        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }
    /**
     * 配置哪些请求不拦截
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**" , "/swagger-ui.html/**");
    }
}