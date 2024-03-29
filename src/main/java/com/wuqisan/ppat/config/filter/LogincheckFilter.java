package com.wuqisan.ppat.config.filter;

import com.alibaba.fastjson.JSON;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.manage.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "LogincheckFilter",urlPatterns = {"/*"})
@Slf4j
public class LogincheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher ANT_PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
       //1、获取本次请求的URI
        String requestURI = httpServletRequest.getRequestURI();//backend/index.html
        //1.1定义不需要处理的路径
        String[] urls ={"/manage/login/login","/manage/login/logout", "/ppat/**","/base/test"};
        //2..判断本次请求是否需要处理
        boolean flag = check(requestURI, urls);
        //3..如果不需要处理..则直接放行
        if (flag) {
/*
            log.info("本次请求不需要处理");
*/
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        long id = Thread.currentThread().getId();
/*
        log.info("线程id:{}",id);
*/
        //4..判断登录状态...如果已登录...则直接放行
        //判断后台
        //4.1用户信息放入threadlocal
        User user = (User) httpServletRequest.getSession().getAttribute("userInfo");
        httpServletRequest.getSession().getAttribute("userInfo");
        if (null != user) {
            BaseContext.setUser(user);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //5..如果未登录则返回未登录结果,通过输出流想客户端响应数据
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN").add("url",httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort())));
        return;
    }


    public boolean check(String requestUri,String[] urls){
        for (String url : urls) {
            boolean match = ANT_PATH_MATCHER.match(url, requestUri);
            if (match){
                return true;
            }
        }
        return false;
    }
}
