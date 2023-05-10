package com.wuqisan.ppat.manage.mapper;

import com.wuqisan.ppat.manage.bean.GradeClass;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassMapper {

    List<GradeClass> getClassList(Map<String, String> pageMap);

    void addClass(GradeClass gradeClass);

    GradeClass queryClassById(String id);

    void deleteClass(String id);
    void updateClassById(Map<String, String> pageMap);
}
