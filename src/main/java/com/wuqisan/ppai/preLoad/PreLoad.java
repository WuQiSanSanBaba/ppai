package com.wuqisan.ppai.preLoad;

import com.wuqisan.ppai.preLoad.service.ConfigService;
import com.wuqisan.ppai.preLoad.service.DictService;
import com.wuqisan.ppai.preLoad.service.ParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class PreLoad {
    @Autowired
    DictService dictService;
    @Autowired
    ParamService paramService;
    @Autowired
    ConfigService configService;

    @PostConstruct
    public void init() {
        dictService.loadDict();
        paramService.loadParam();
        configService.loadConfig();
    }
}
