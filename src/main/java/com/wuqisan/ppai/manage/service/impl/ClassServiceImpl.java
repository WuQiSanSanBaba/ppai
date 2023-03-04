package com.wuqisan.ppai.manage.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.ClassInfo;
import com.wuqisan.ppai.manage.mapper.ClassMapper;
import com.wuqisan.ppai.manage.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    ClassMapper classMapper;
    @Override
    public PageInfo<ClassInfo> getClassListPage(Map<String, String> pageMap) {
        List<ClassInfo> list= classMapper.getClassList(pageMap);
        return new PageInfo<>(list);
    }

    @Override
    public List<ClassInfo> getClassList(Map<String, String> pageMap) {
        return classMapper.getClassList(pageMap);
    }

    /**
     * 新增班级
     *
     * @param classInfo
     */
    @Override
    public void addClass(ClassInfo classInfo) {
        classMapper.addClass(classInfo);
    }

    @Override
    public ClassInfo queryClassById(String id) {
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
