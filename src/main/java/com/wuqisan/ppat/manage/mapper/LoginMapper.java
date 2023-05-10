package com.wuqisan.ppat.manage.mapper;

import com.wuqisan.ppat.manage.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    User getUserById(Long id);

    User getUserByUserAccount(String useraccount);

    User getUserByUsernameAndPassword(User user);
}
