package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.vo.CourseInfoVo;
import com.wlk.service.edu.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    //添加课程基本信息

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);

        return R.ok().data("courseId",id);
    }
}

