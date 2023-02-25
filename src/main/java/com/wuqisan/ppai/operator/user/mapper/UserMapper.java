package com.wuqisan.ppai.operator.user.mapper;

import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserInfo getUserById(Long id);

    UserInfo getUserByUsername(String username);

    UserInfo getUserByUsernameAndPassword(UserInfo userInfo);

    List<MenuInfo> getMenuList(Long id);

    Integer checkAuth(Long id,String authId);
}
