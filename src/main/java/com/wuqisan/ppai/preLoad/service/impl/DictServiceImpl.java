package com.wuqisan.ppai.preLoad.service.impl;

import com.wuqisan.ppai.common.Utils.CacheUtils;
import com.wuqisan.ppai.preLoad.bean.DictItem;
import com.wuqisan.ppai.preLoad.mapper.DictMapper;
import com.wuqisan.ppai.preLoad.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DictServiceImpl implements DictService {
    @Autowired
    DictMapper dictMapper;

    @Override
    public List<DictItem> getDictListByGroupId(String groupId){
      return dictMapper.getDictListByGroupId(groupId);
    }
    //获取字典表所有分组
    @Override
    public List<String> getGroupList() {
        return dictMapper.getGroupList();
    }


    @Override
    public void loadDict() {
        log.error("开始加载字典组-------");
        //根据id把字典组存进缓存里
        this.putDictListById();
        //根据groupId吧字典组放进缓存里
        this.putDictListByGroupId();
        log.error("字典组加载成功");
    }

    private void putDictListById() {
        //查询全部不需要分组，所以传null
        List<DictItem> dictList = getDictListByGroupId(null);
        Map<String,DictItem> dictMap=new HashMap<>();
        for (DictItem dictItem : dictList) {
            dictMap.put(dictItem.getDictId(),dictItem);
        }
        CacheUtils.put("dictMap",dictMap);
    }
    private void putDictListByGroupId() {
        List<String> groupList = this.getGroupList();
        Map<String,Map<String,DictItem>> groupMap=new HashMap<>();
        for (String groupId : groupList) {
            List<DictItem> dictList = this.getDictListByGroupId(groupId);
            Map<String,DictItem> dictMap=new HashMap<>();
            for (DictItem dictItem : dictList) {
                dictMap.put(dictItem.getDictId(),dictItem);
            }
            groupMap.put(groupId,dictMap);
        }
        CacheUtils.put("groupMap",groupMap);

    }


}
