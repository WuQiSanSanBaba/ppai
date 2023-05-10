package com.wuqisan.ppat.common.mapper;

import com.wuqisan.ppat.manage.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMapper {
    List<User> getUserByCondition(Map<String, String> pageMap);
}
