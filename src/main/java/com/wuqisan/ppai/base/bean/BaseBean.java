package com.wuqisan.ppai.base.bean;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BaseBean {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
