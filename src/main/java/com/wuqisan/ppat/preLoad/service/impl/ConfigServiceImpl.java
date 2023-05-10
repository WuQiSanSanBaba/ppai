package com.wuqisan.ppat.preLoad.service.impl;

import com.wuqisan.ppat.common.Utils.CacheUtils;
import com.wuqisan.ppat.preLoad.bean.Param;
import com.wuqisan.ppat.preLoad.mapper.ParamMapper;
import com.wuqisan.ppat.preLoad.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    ParamMapper paramMapper;
    @Override
    public void loadConfig() {
        List<Param> paramList = paramMapper.getParamList();
        log.info("开始加载参数....");
        Map<String,String> paramMap=new HashMap<>();
        for (Param param : paramList) {
            paramMap.put(param.getParamId(), param.getParamValue());
        }
        CacheUtils.put("paramMap",paramMap);
        log.info("参数加载完成....");
    }
}
