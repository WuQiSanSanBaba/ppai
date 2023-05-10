package com.wuqisan.ppat.preLoad.service.impl;

import com.wuqisan.ppat.common.Utils.CacheUtils;
import com.wuqisan.ppat.preLoad.service.ParamService;
import com.wuqisan.ppat.system.bean.Config;
import com.wuqisan.ppat.system.mapper.ConfigMapper;
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
        log.info("开始加载配置参数....");
        Map<String,String> configMap=new HashMap<>();
        for (Config config : configList) {
            configMap.put(config.getConfigId(),config.getValue());
        }
        CacheUtils.put("configMap",configMap);
        log.info("配置加载完成....");
    }
}
