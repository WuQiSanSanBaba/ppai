package com.wuqisan.ppai.manage.mapper;

import com.wuqisan.ppai.manage.bean.ClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassMapper {

    List<ClassInfo> getClassList(Map<String, String> pageMap);

    void addClass(ClassInfo classInfo);

    ClassInfo queryClassById(String id);

    void deleteClass(String id);
    void updateClassById(Map<String, String> pageMap);
}
