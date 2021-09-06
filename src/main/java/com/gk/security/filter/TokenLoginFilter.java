package com.gk.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.security.domain.SecurityUser;
import com.gk.security.securityConfug.TokenManager;
import com.gk.security.utils.R;
import com.gk.security.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author guokui
 * @class srs
 * @date 2021/9/6 14:07
 */
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;
    public TokenLoginFilter(AuthenticationManager authenticationManager,
                            TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new
                AntPathRequestMatcher("/admin/acl/login","POST"));
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(),
                    User.class);
            return authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new
                    ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 登录成功
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws
            IOException, ServletException {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        String token =
                tokenManager.createToken(user.getCurrentUserInfo().getUsername());

        redisTemplate.opsForValue().set(user.getCurrentUserInfo().getUsername(),
                user.getPermissionValueList());
        ResponseUtil.out(res, R.ok().data("token", token));
    }
    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(response, R.error());
    }
}
