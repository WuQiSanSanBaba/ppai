package com.wuqisan.ppai.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.ClassInfo;
import com.wuqisan.ppai.system.bean.Config;
import com.wuqisan.ppai.system.mapper.ConfigMapper;
import com.wuqisan.ppai.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConfigerviceImpl implements ConfigService {
    @Autowired
    ConfigMapper configMapper;
    @Override
    public PageInfo<Config> getConfigList(Map<String, String> pageMap) {
        List<Config> list= configMapper.getConfigList(pageMap);
        return new PageInfo<>(list);
    }


    @Override
    public void updateConfigById(Map<String, String> pageMap) {
        configMapper.updateConfigById(pageMap);
    }

    @Override
    public Config queryConfigById(String id) {
      return   configMapper.queryConfigById(id);
    }
}
