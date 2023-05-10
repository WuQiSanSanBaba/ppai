package com.wuqisan.ppat.manage.service.impl;

import com.wuqisan.ppat.manage.bean.Menu;
import com.wuqisan.ppat.manage.mapper.MenuMapper;
import com.wuqisan.ppat.manage.service.MenuService;
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
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getMenuList(Long userId) {
        return menuMapper.getMenuList(userId);
    }


}
