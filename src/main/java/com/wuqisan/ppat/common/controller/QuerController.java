package com.wuqisan.ppat.common.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.common.Utils.DictUtils;
import com.wuqisan.ppat.common.service.QueryService;
import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.preLoad.bean.DictItem;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/query/")
@Slf4j
public class QuerController {
    @Autowired
    QueryService queryService;

    @RequestMapping("getUserByCondition")
    @ApiOperation("根据条件获取用户信息")
    public R<List<User>> getUserByCondition(@RequestBody(required = false) Map<String, String> params) {
        List<User> userList = queryService.getUserByCondition(params);
        return R.success(userList);
    }

    @RequestMapping("getDictListByGroupId/{groupId}")
    @ApiOperation("获取字典组列表")
    public R<List<DictItem>> getDictListByGroupId(@PathVariable String groupId) {
        return R.success(DictUtils.getGroupList(groupId));
    }
}
