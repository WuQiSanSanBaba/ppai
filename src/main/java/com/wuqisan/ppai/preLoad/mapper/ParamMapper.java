package com.wuqisan.ppai.preLoad.mapper;

import com.wuqisan.ppai.preLoad.bean.ParamInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParamMapper {
    List<ParamInfo> getParamList();
}
