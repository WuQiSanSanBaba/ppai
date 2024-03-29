package com.wuqisan.ppat.common.Utils;

import com.wuqisan.ppat.preLoad.service.ConfigService;
import com.wuqisan.ppat.preLoad.service.impl.ConfigServiceImpl;

import java.util.Map;

public class ConfigUtils {

    private static final ConfigService configService = SpringUtils.getBean(ConfigServiceImpl.class);

    public static void reload() {
        configService.loadConfig();
    }

    public static String  getValueById(String configId) {
        Map<String,String> configMap = CacheUtils.get("configMap");
        String s = configMap.get(configId);
        if (s==null){
            return "";
        }else {
            return s;
        }
    }


}
