package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.Annotation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnotationMapper {
    void addAnnotacionList(List<Annotation> list);

}
