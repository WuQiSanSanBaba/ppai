package com.wuqisan.ppai.manage.mapper;

import com.wuqisan.ppai.manage.bean.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuInfo> getMenuList(Long userId);

    List<MenuInfo> getChildrenMenu(Long userId, String parentId);
}
