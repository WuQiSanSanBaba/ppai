package com.wuqisan.ppai.config.mybatis;

import com.wuqisan.ppai.config.mybatis.autoFillField.AotuFillField;
import com.wuqisan.ppai.config.mybatis.sqlTimeCul.SqlExecuteTimeCountInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {
    /**
     *  自定义插入CreateTime ,UpdateTime 时间
     * @return
     */
    @Bean
    public AotuFillField aotuFillField() {
        return new AotuFillField();
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
