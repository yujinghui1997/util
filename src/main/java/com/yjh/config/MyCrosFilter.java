package com.yjh.config;

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
    private String whiteList;

    public MyCrosFilter(Boolean open, String whiteList) {
        this.open = open;
        this.whiteList = whiteList;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (this.open) {
            String origin = whiteList;
            if("*".equals(origin)){
                origin =  request.getHeader("origin");
                if (StrUtil.hasBlank(origin)){
                    origin = "*";
                }
            } else {
                String o = request.getHeader("origin");
                if (origin.contains(o)){
                    origin = o;
                } else {
                    origin = "";
                }
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
