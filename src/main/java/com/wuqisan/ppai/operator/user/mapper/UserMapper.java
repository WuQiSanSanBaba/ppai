package com.wuqisan.ppai.operator.user.mapper;

import com.wuqisan.ppai.operator.user.bean.ClassInfo;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {



    Integer checkAuth(Long id,String authId);

    List<UserInfo> getUserList(Map<String, String> map);

    List<ClassInfo> getClassList(Map<String, String> pageMap);
}
