package com.wuqisan.ppai.preLoad.service.impl;

import com.wuqisan.ppai.common.Utils.CacheUtils;
import com.wuqisan.ppai.preLoad.bean.ParamInfo;
import com.wuqisan.ppai.preLoad.mapper.ParamMapper;
import com.wuqisan.ppai.preLoad.service.ConfigService;
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
        List<ParamInfo> paramList = paramMapper.getParamList();
        log.error("开始加载参数....");
        Map<String,String> paramMap=new HashMap<>();
        for (ParamInfo paramInfo : paramList) {
            paramMap.put(paramInfo.getParamId(),paramInfo.getParamValue());
        }
        CacheUtils.put("paramMap",paramMap);
        log.error("参数加载完成....");
    }
}
