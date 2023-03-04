package com.wuqisan.ppai.config.mybatis.autoFillField;


import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Properties;


import com.wuqisan.ppai.base.context.BaseContext;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.CreateTime;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.CreateUser;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.UpdateTime;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.UpdateUser;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;


/**
 * 自动填充字段拦截器
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })})
public class AotuFillField implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // sql 类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        // 获取私有成员变量
        Field[] declaredFields = parameter.getClass().getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(CreateTime.class) != null) {
                // insert || update 语句插入 createTime
                if (SqlCommandType.INSERT.equals(sqlCommandType)||SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }
            if (field.getAnnotation(UpdateTime.class) != null) {
                // insert 或 update 语句插入 updateTime
                if ( SqlCommandType.UPDATE.equals(sqlCommandType) || SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }
            if (field.getAnnotation(CreateUser.class) != null) {
                // insert 或 update 语句插入 updateTime
                if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, BaseContext.getCurrentUserInfo().getUserId());
                }
            }
            if (field.getAnnotation(UpdateUser.class) != null) {
                // insert 或 update 语句插入 updateTime
                if ( SqlCommandType.UPDATE.equals(sqlCommandType) || SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, BaseContext.getCurrentUserInfo().getUserId());
                }
            }
        }
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
