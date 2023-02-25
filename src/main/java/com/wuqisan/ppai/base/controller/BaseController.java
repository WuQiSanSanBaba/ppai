package com.wuqisan.ppai.base.controller;

import com.wuqisan.ppai.operator.user.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

}
