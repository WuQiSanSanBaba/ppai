package com.wuqisan.ppai.operator.user.mapper;

import com.wuqisan.ppai.operator.user.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    UserInfo getUserById(Long id);

    UserInfo getUserByUsername(String username);

    UserInfo getUserByUsernameAndPassword(UserInfo userInfo);
}
