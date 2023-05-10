package com.wuqisan.ppat.manage.mapper;

import com.wuqisan.ppat.manage.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {



    Integer checkAuth(Long id,String authId);




    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
