package com.wuqisan.ppai.system.mapper;

import com.wuqisan.ppai.system.bean.Config;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConfigMapper {

    List<Config> getConfigList(Map<String, String> pageMap);



    void updateConfigById(Map<String, String> pageMap);

    Config queryConfigById(String id);
}
