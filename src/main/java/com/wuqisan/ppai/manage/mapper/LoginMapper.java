package com.wuqisan.ppai.manage.mapper;

import com.wuqisan.ppai.manage.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    UserInfo getUserById(Long id);

    UserInfo getUserByUsername(String username);

    UserInfo getUserByUsernameAndPassword(UserInfo userInfo);
}
