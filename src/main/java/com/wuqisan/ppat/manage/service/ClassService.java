package com.wuqisan.ppat.manage.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.manage.bean.GradeClass;

import java.util.List;
import java.util.Map;

public interface ClassService {
    PageInfo<GradeClass> getClassListPage(Map<String, String> pageMap);
    List<GradeClass> getClassList(Map<String, String> pageMap);

    /**
     * 新增班级
     * @param gradeClass
     */
    void addClass(GradeClass gradeClass);

    GradeClass queryClassById(String id);

    void deleteClass(String id);

    void updateClassById(Map<String, String> pageMap);

}
