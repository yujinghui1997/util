package com.yjh.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "MyCrosFilter",urlPatterns = "*")
public class MyCrosFilter implements Filter {

    @Autowired
    private MyCrosFilterProperties myCrosProperties;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        if (myCrosProperties.getCros()){
            String origin =   request.getHeader("origin");
            if (StrUtil.hasBlank(origin)){
                origin = "*";
            }
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Allow-Origin",origin);
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException { }
}
