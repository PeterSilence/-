package com.juane.filter;

import com.juane.common.BaseContext;
import com.juane.common.R;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求的URL
        String requestURI = request.getRequestURI();
        //放过不需要的请求
        String[] urls = new String[]{
                "/administrateur/login",
                "/administrateur/logout",
                "/usager/login",
                "/usager/logout",
                "/usager/enregister",
                "/common/**",
                "front/**"
        };
        log.info("拦截到请求：{}",requestURI);
        //判断本次请求是否需要处理
        if (check(urls,requestURI)){
            //无需处理，直接放行
            filterChain.doFilter(request,response);
            return;
        }
        //判断管理员登录状态，如果已经登录，则直接放行
        if (request.getSession().getAttribute("administrateur") != null){
            String administrateurId = (String) request.getSession().
                    getAttribute("administrateur");
            BaseContext.setCurrentId(administrateurId);
            filterChain.doFilter(request,response);
            return;
        }
        //判断用户登录状态，如果已经登录，放行之
        if (request.getSession().getAttribute("usager") != null){
            String usagerId = (String) request.getSession().getAttribute("usager");
            BaseContext.setCurrentId(usagerId);
            filterChain.doFilter(request,response);
            return;
        }
        //如果没有登陆的话就返回返回提示信息
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(R.error("未检测到您的登录信息！")));
    }

    private boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url,requestURI))
                return true;
        } return false;
    }
}
