package com.wuqisan.ruijiwaimai.config.filter;

import com.alibaba.fastjson.JSON;
import com.wuqisan.ruijiwaimai.common.Utils.BaseContext;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String[] urls ={"/employee/login","/employee/logout","/backend/**","/front/**","/outapi/**",
        "/user/sendMsg","/user/login"};
        //2..判断本次请求是否需要处理
        boolean flag = check(requestURI, urls);
        //3..如果不需要处理..则直接放行
        if (flag) {
            log.info("本次请求不需要处理");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //4...判断登录状态...如果已登录...则直接放行
        //4.1判断后台
        long id = Thread.currentThread().getId();
        Long employeeId = (Long) httpServletRequest.getSession().getAttribute("employee");
        log.info("线程id:{}",id);
        if (null != employeeId) {
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //4.2判断用户
        Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
        if (null != userId) {
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //5、.如果未登录则返回未登录结果,通过输出流想客户端响应数据
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
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
