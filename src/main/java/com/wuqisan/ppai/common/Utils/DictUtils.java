package com.wuqisan.ppai.common.Utils;

import com.wuqisan.ppai.preLoad.bean.DictItem;
import com.wuqisan.ppai.preLoad.service.DictService;
import com.wuqisan.ppai.preLoad.service.impl.DictServiceImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DictUtils {
    private static final DictService dictService = SpringUtils.getBean(DictServiceImpl.class);


    public static void   reload() {
        dictService.loadDict();
    }

    public static String  getDictName(String dictId) {
        String name = getDictItem(dictId).getName();
        if (name==null){
            return "";
        }else {
            return name;
        }
    }
    public static String  getDictName(String dictId,String grouId) {
        DictItem dictItem = getDictItem(dictId, grouId);

        if (dictItem==null){
            return "";
        }else {
            return dictItem.getName();
        }
    }
    //
    public static List<DictItem> getGroupList(String groupId){
        Map<String,Map<String,DictItem>> groupMap = CacheUtils.get("groupMap");
        Map<String, DictItem> dictItemMap = groupMap.get(groupId);
        List<DictItem> list=new ArrayList<>();
        for (Map.Entry<String, DictItem> stringDictItemEntry : dictItemMap.entrySet()) {
            DictItem value = stringDictItemEntry.getValue();
            list.add(value);
        }

        return   list;
    }

    public static DictItem  getDictItem(String dictId) {
        Map<String,DictItem> dictItemMap = CacheUtils.get("dictMap");
        DictItem dictItem = dictItemMap.get(dictId);
        return dictItem;
    }
    public static DictItem  getDictItem(String dictId,String grouId) {
        Map<String,Map<String,DictItem>> groupMap = CacheUtils.get("groupMap");
        Map<String, DictItem> grouId_ = groupMap.get(grouId);
        DictItem dictItem= grouId_.get(dictId);
       return dictItem;
    }
}
