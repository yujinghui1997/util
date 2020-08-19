package com.yjh.comp;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class MyCrosFilter implements Filter {

    public String origin;
    private String[] headers;

    public MyCrosFilter(){

    }
    public MyCrosFilter(String origin,String[] headers){
        this.origin = origin;
        this.headers = headers;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<String> list = Arrays.asList(headers);
        String headers = list.stream().collect(Collectors.joining(","));
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", headers);
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
