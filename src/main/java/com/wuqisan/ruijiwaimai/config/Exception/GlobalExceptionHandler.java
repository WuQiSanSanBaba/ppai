package com.wuqisan.ruijiwaimai.config.Exception;

import com.wuqisan.ruijiwaimai.common.Exception.CustomException;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;


//拦截加了RestController   Controller 的注解的异常类
@ControllerAdvice(annotations = {RestController.class, Controller.class, Service.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    
    /*
    异常处理方法--处理sql异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split(" ");
           String message= split[2]+"已存在";
          return R.error(message);
        }
        return R.error("未知错误");
    }
    /**
     * 异常处理方法--业务异常
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
