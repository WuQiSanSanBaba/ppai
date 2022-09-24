package com.wuqisan.ruijiwaimai.config.MybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mpp提供的元数据处理类，自动填充共有字段数据进数据库
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
    插入操作自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("元数据-=insert");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",new Long(123));
        metaObject.setValue("updateUser",new Long(123));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("元数据-=update");
        log.info(metaObject.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id:{}",id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",new Long(123));

    }
}
