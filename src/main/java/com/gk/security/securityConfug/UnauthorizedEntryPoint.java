package com.gk.security.securityConfug;

import com.gk.security.utils.R;
import com.gk.security.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guokui
 * @class srs
 * @date 2021/9/6 14:10
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse
            response,
                         AuthenticationException authException) throws
            IOException, ServletException {
        ResponseUtil.out(response, R.error());
    } 
}