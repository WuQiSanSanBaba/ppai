package com.wuqisan.ppai.operator.user.controller;

import com.wuqisan.ppai.base.bean.DictItem;
import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.context.BaseContext;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operator/menu/")
public class MenuController extends BaseController {
    @Autowired
    MenuService menuService;

    @RequestMapping("getMenuList")
    @ApiOperation(value = "根据用户ID获取其拥有的菜单")
    public R<List<DictItem>> getMenuList(){
        List<DictItem> menuList = menuService.getMenuList();
        if (menuList==null){
            return R.error("您没有菜单权限，请联系管理员");
        }else {
            return R.success(menuList);
        }
    }
    @RequestMapping("getChildrenMenu/{parentId}")
    @ApiOperation(value = "根据用户ID获取其拥有的菜单")
    public R<List<MenuInfo>> getChildrenMenu(@PathVariable String parentId){
        Long userId = BaseContext.getCurrentUserInfo().getId();
        List<MenuInfo> menuList = menuService.getChildrenMenu(userId,parentId);
        if (menuList==null){
            return R.error("没有子菜单，请联系管理员");
        }else {
            return R.success(menuList);
        }
    }
}
