package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Teacher;
import com.wlk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-24
 */
@Api()
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    //注入service
    @Autowired
    private TeacherService teacherService;

    //查询讲师所有数据
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //根据id逻辑删除
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable("id") String id) {
        if (teacherService.removeById(id)) {
            return R.ok();
        } else
            return R.error();
    }
}

