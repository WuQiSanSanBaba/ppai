package com.wuqisan.ppai.base.bean;

import com.wuqisan.ppai.config.mybatis.autoFillField.anno.CreateTime;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.CreateUser;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.UpdateTime;
import com.wuqisan.ppai.config.mybatis.autoFillField.anno.UpdateUser;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BaseBean {

    private static final long serialVersionUID = 1L;

    @CreateTime
    private LocalDateTime createTime;

    @UpdateTime
    private LocalDateTime updateTime;

    @CreateUser
    private Long createUser;

    @UpdateUser
    private Long updateUser;

    private Integer  status;
}
