package com.yjh.comp;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyCrosFilter implements Filter {

    private Boolean open;

    public MyCrosFilter(Boolean open) {
        this.open = open;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setCharacterEncoding("utf-8");
        if (this.open) {
            String origin  =  request.getHeader("origin");
            if (StrUtil.hasBlank(origin)){
                origin = "*";
            }
            response.setCharacterEncoding("utf-8");
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
