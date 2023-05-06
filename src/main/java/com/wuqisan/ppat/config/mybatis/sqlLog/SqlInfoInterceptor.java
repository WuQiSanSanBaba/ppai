package com.wuqisan.ppat.config.mybatis.sqlLog;

import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.CreateTime;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.CreateUser;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.UpdateTime;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.UpdateUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
 import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        // 拦截包含 MappedStatement 的其他方法
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
@Slf4j
public class SqlInfoInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String statementId = mappedStatement.getId();
        log.info("================================================================");
        log.info("当前执行的sqlid是===》{}", statementId);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof org.apache.ibatis.executor.Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
