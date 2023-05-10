package com.wuqisan.ppat.preLoad.mapper;

import com.wuqisan.ppat.preLoad.bean.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParamMapper {
    List<Param> getParamList();
}
