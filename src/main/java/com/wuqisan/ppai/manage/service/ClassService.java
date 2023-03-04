package com.wuqisan.ppai.manage.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.ClassInfo;

import java.util.List;
import java.util.Map;

public interface ClassService {
    PageInfo<ClassInfo> getClassListPage(Map<String, String> pageMap);
    List<ClassInfo> getClassList(Map<String, String> pageMap);

    /**
     * 新增班级
     * @param classInfo
     */
    void addClass(ClassInfo classInfo);

    ClassInfo queryClassById(String id);

    void deleteClass(String id);

    void updateClassById(Map<String, String> pageMap);

}
