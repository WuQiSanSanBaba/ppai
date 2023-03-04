package com.wuqisan.ppai.manage.mapper;

import com.wuqisan.ppai.manage.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {



    Integer checkAuth(Long id,String authId);



    List<UserInfo> getUserByCondition(Map<String, String> pageMap);

    void addUser(UserInfo userInfo);
}
