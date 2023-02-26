package com.wuqisan.ppai.operator.user.service.impl;

import com.wuqisan.ppai.base.bean.DictItem;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.mapper.MenuMapper;
import com.wuqisan.ppai.operator.user.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    /**
     * 获取用户菜单
     *
     * @param id
     * @return
     */
    @Override
    public List<DictItem> getMenuList() {
        return menuMapper.getMenuList();
    }

    @Override
    public List<MenuInfo> getChildrenMenu(Long userId, String parentId) {
        return menuMapper.getChildrenMenu(userId,parentId);
    }
}
