package com.wuqisan.ppat.config.mybatis;

import com.wuqisan.ppat.config.mybatis.autoFillField.AotuFillFieldInterceptor;
import com.wuqisan.ppat.config.mybatis.sqlLog.SqlExecuteTimeCountInterceptor;
import com.wuqisan.ppat.config.mybatis.sqlLog.SqlInfoInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {
    /**
     *  自定义插入CreateTime ,UpdateTime 时间
     * @return
     */
    @Bean
    public AotuFillFieldInterceptor aotuFillField() {
        return new AotuFillFieldInterceptor();
    }

    @Bean
    public SqlInfoInterceptor sqlInfoInterceptor(){
        return new SqlInfoInterceptor();
    }
    /**
     * 统计sql执行时间时间
     * @return
     */
    @Bean
    public SqlExecuteTimeCountInterceptor sqlExecuteTimeCountInterceptor(){
        return new SqlExecuteTimeCountInterceptor();
    }


}
