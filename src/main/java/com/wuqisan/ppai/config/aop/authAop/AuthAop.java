package com.wuqisan.ppai.config.aop.authAop;

import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.context.BaseContext;
import com.wuqisan.ppai.common.Utils.CheckUtils;
import com.wuqisan.ppai.manage.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 方法级鉴权类
 */
@Slf4j
@Aspect
@Component
public class AuthAop {
    @Pointcut("@annotation(com.wuqisan.ppai.config.aop.authAop.AuthCheck)")
    public void authCheck() {

    }

    /**
     * 环绕通知鉴权
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("authCheck()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuthCheck authCheck = method.getAnnotation(AuthCheck.class);
        String authId = authCheck.value();
        UserInfo currentUserInfo = BaseContext.getCurrentUserInfo();
        boolean b = CheckUtils.checkAuth(currentUserInfo.getUserId(), authId);
        if (b){
          result=  joinPoint.proceed();

        }else {
            result= R.error("你没有权限！");
        }
        return result;
    }

}
