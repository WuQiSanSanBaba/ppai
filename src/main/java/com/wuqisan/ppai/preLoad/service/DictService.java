package com.wuqisan.ppai.preLoad.service;

import com.wuqisan.ppai.preLoad.bean.DictItem;

import java.util.List;

public interface DictService {
    List<DictItem> getDictListByGroupId(String groupId);
    //获取字典表所有分组
    List<String> getGroupList();
    void loadDict();
}
