package com.wuqisan.ppat.config.aop.authAop;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.common.Utils.CheckUtils;
import com.wuqisan.ppat.manage.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
    @Pointcut("@annotation(com.wuqisan.ppat.config.aop.authAop.AuthCheck)")
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
        String msg = authCheck.msg();
        User currentUser = BaseContext.getUser();
        boolean b = CheckUtils.checkAuth(currentUser.getUserId(), authId);
        if (b){
          result=  joinPoint.proceed();

        }else {
            if (StringUtils.isBlank(msg)){
            result= R.error("你没有权限！");
            }else{
                result= R.error(msg);
            }
        }
        return result;
    }

}
