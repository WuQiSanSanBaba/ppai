package com.wuqisan.ppat.manage.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.manage.bean.GradeClass;
import com.wuqisan.ppat.manage.mapper.ClassMapper;
import com.wuqisan.ppat.manage.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassMapper classMapper;
    @Override
    public PageInfo<GradeClass> getClassListPage(Map<String, String> pageMap) {
        List<GradeClass> list= classMapper.getClassList(pageMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<GradeClass> getClassList(Map<String, String> pageMap) {
        return classMapper.getClassList(pageMap);
    }

    /**
     * 新增班级
     *
     * @param gradeClass
     */
    @Override
    public void addClass(GradeClass gradeClass) {
        classMapper.addClass(gradeClass);
    }

    @Override
    public GradeClass queryClassById(String id) {
        return classMapper.queryClassById(id);
    }

    @Override
    public void deleteClass(String id) {
        classMapper.deleteClass(id);
    }

    @Override
    public void updateClassById(Map<String, String> pageMap) {
        classMapper.updateClassById(pageMap);
    }
}
