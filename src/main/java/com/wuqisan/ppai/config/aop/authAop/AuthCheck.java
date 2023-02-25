package com.wuqisan.ppai.config.aop.authAop;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于参数或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    String value() default "";

}
