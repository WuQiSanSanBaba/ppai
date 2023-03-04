package com.wuqisan.ppai.preLoad.mapper;

import com.wuqisan.ppai.preLoad.bean.DictItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictMapper {
    List<DictItem> getDictListByGroupId(String groupId);

    List<String> getGroupList();
}
