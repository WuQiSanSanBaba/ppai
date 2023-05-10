package com.wuqisan.ppat.config.mybatis.autoFillField;


import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;


import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.CreateTime;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.CreateUser;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.UpdateTime;
import com.wuqisan.ppat.config.mybatis.autoFillField.anno.UpdateUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;


/**
 * 自动填充字段拦截器
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class AotuFillFieldInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // sql 类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取参数
        Object parameter = invocation.getArgs()[1];
        //我发现 foreach 批量插入的时候会是hashmap 然后里边有个3list 然后直接遍历吧
        if (parameter instanceof HashMap) {
            HashMap map = (HashMap) parameter;
            //遍历map
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                //只有是数组的情况下才填充
                if (entry.getValue() instanceof Collection) {
                    ArrayList value = (ArrayList) entry.getValue();
                    for (Object o : value) {
                        //判断o是不是数组
                        fillField(sqlCommandType, o);
                    }
                }

            }
        }
        // 如果是数组的情况下才填充
        else if (parameter instanceof Collection) {
            for (Object o : (Collection) parameter) {
                fillField(sqlCommandType, o);
            }
        }
        //单个插入的时候
        else {
            fillField(sqlCommandType, parameter);
        }
        return invocation.proceed();
    }

    private static void fillField(SqlCommandType sqlCommandType, Object parameter) throws IllegalAccessException {
        // 获取私有成员变量
        Field[] declaredFields = parameter.getClass().getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(CreateTime.class) != null) {
                // insert || update 语句插入 createTime
                if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }
            if (field.getAnnotation(UpdateTime.class) != null) {
                // insert 或 update 语句插入 updateTime
                if (SqlCommandType.UPDATE.equals(sqlCommandType) || SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, LocalDateTime.now());
                }
            }
            if (field.getAnnotation(CreateUser.class) != null) {
                // insert 或 update 语句插入 updateTime
                if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, BaseContext.getUser().getUserId());
                }
            }
            if (field.getAnnotation(UpdateUser.class) != null) {
                // insert 或 update 语句插入 updateTime
                if (SqlCommandType.UPDATE.equals(sqlCommandType) || SqlCommandType.INSERT.equals(sqlCommandType)) {
                    field.setAccessible(true);
                    field.set(parameter, BaseContext.getUser().getUserId());
                }
            }
        }
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
