package com.wuqisan.ppat.classroom.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.service.SubjectService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import com.wuqisan.ppat.config.aop.authAop.AuthCheck;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classroom/subject/")
@Slf4j
public class SubjectController extends BaseController {
    @Autowired
    SubjectService subjectService;

    
    @PostMapping("getSUbjectList")
    @AuthCheck("teacher")//检查是否有Root权限
    @ApiOperation("查询所有主题")
    public R<PageInfo<Subject>> getSUbjectList(@RequestBody(required = false) Map<String, String> pageMap) {
        setPage(pageMap);
        PageInfo<Subject> subjectList = subjectService.getSUbjectList(pageMap);
        return R.success(subjectList);
    }

    @PostMapping("addSubject")
    @AuthCheck("teacher")//检查是否有Root权限
    @ApiOperation("增加主题")
    public R<String> addSubject(@RequestBody(required = true) Subject subject) {
        subject.setSubjectId(CommonUtils.generateKey15());
        subjectService.addSubject(subject);
        return R.success("添加成功");
    }
    @PostMapping("updateSubject")
    @AuthCheck("teacher")//检查是否有Root权限
    @ApiOperation("修改主题")
    public R<String> updateSubject(@RequestBody(required = true) Subject subject) {
        subjectService.updateSubject(subject);
        return R.success("修改成功");
    }

    @GetMapping("deleteSubject/{subjectId}")
    @AuthCheck("teacher")//检查是否有Root权限
    @ApiOperation("删除主题")
    public R<String> deleteSubject(@PathVariable String subjectId) {
        try {
            subjectService.deleteSubject(subjectId);
            return R.success("删除成功");
        } catch (Exception e) {
            log.error("删除失败" + e);
            return R.error("删除失败" + e);
        }
    }

    @PostMapping("updateSubjectStatus")
    @AuthCheck("teacher")//检查是否有Root权限
    @ApiOperation("修改主题状态")
    public R<String> updateSubjectStatus(@RequestBody(required = true) Subject subject) {
        try {
            subjectService.updateSubjectStatus(subject);
            return R.success("修改主题状态成功");
        } catch (Exception e) {
            log.error("修改主题状态失败" + e);
            return R.error("修改主题状态失败" + e);
        }
    }

    @GetMapping("getSUbjectById/{subjectId}")
    @ApiOperation("根据id获取主题")
    public R<Subject> getSubjectById(@PathVariable Long subjectId) {
        Subject subject = subjectService.getSubjectById(subjectId);
        return R.success(subject);
    }
    @GetMapping("getSujectsByStage/{stage}")
    @ApiOperation("根据阶段获取主题")
    public R<List<Subject>> getSujectsByStage(@PathVariable String stage) {
        List<Subject> subjects = subjectService.getSujectsByStage(stage);
        return R.success(subjects);
    }

}
