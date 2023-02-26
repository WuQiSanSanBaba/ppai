package com.wuqisan.ppai.operator.user.mapper;

import com.wuqisan.ppai.base.bean.DictItem;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<DictItem> getMenuList();

    List<MenuInfo> getChildrenMenu(Long userId, String parentId);
}
