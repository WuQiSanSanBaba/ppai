package com.wuqisan.ppat.system.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.system.bean.Config;

import java.util.Map;

public interface ConfigService {
    PageInfo<Config> getConfigList(Map<String, String> pageMap);

    void updateConfigById(Map<String, String> pageMap);

    Config queryConfigById(String id);
}
