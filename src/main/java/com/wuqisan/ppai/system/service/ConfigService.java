package com.wuqisan.ppai.system.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.ClassInfo;
import com.wuqisan.ppai.system.bean.Config;

import java.util.Map;

public interface ConfigService {
    PageInfo<Config> getConfigList(Map<String, String> pageMap);

    void updateConfigById(Map<String, String> pageMap);

    Config queryConfigById(String id);
}
