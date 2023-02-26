package com.wuqisan.ppai.operator.user.service;

import com.wuqisan.ppai.base.bean.DictItem;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;

import java.util.List;

public interface MenuService {
    public List<DictItem> getMenuList();

    public List<MenuInfo> getChildrenMenu(Long userId, String id);
}
