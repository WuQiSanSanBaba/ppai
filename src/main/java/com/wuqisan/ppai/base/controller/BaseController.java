package com.wuqisan.ppai.base.controller;

import com.github.pagehelper.PageHelper;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/base")
public class BaseController {
    public  ServletRequestAttributes getRequestAttributes() {

        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public  HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    public HttpSession getSession(){
        return  getRequest().getSession();
    }

    public void setPage(Map<String,String> pageMap){
        int page = Integer.parseInt(pageMap.get("page"));
        int pageSize = Integer.parseInt(pageMap.get("pageSize"));
        PageHelper.startPage(page,pageSize);
    }

}
