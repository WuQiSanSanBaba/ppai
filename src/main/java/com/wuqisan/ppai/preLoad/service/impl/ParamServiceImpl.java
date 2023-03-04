package com.wuqisan.ppai.preLoad.service.impl;

import com.wuqisan.ppai.common.Utils.CacheUtils;
import com.wuqisan.ppai.preLoad.bean.ParamInfo;
import com.wuqisan.ppai.preLoad.mapper.ParamMapper;
import com.wuqisan.ppai.preLoad.service.ParamService;
import com.wuqisan.ppai.system.bean.Config;
import com.wuqisan.ppai.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ParamServiceImpl implements ParamService {
    @Autowired
    ConfigMapper configMapper;
    @Override
    public void loadParam() {
        List<Config> configList = configMapper.getConfigList(null);
        log.error("开始加载配置参数....");
        Map<String,String> configMap=new HashMap<>();
        for (Config config : configList) {
            configMap.put(config.getConfigId(),config.getValue());
        }
        CacheUtils.put("configMap",configMap);
        log.error("配置加载完成....");
    }
}
