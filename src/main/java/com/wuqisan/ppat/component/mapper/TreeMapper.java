package com.wuqisan.ppat.component.mapper;

import com.wuqisan.ppat.manage.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreeMapper {
    List<User> getStudentList(String classId);
}
