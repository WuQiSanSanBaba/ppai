package com.wuqisan.ppai.common.Utils;

import com.wuqisan.ppai.preLoad.service.ParamService;
import com.wuqisan.ppai.preLoad.service.impl.ParamServiceImpl;

import java.util.Map;

public class ParamUtils {

    private static final ParamService paramService = SpringUtils.getBean(ParamServiceImpl.class);

    public static void reload() {
        paramService.loadParam();
    }

    public static String  getParamValueById(String paramId) {
        Map<String,String> paramMap = CacheUtils.get("paramMap");
        String s = paramMap.get(paramId);
        if (s==null){
            return "";
        }else {
            return s;
        }
    }


}
