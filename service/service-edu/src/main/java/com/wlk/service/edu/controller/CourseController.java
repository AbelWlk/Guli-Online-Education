package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.vo.CourseInfoVo;
import com.wlk.service.edu.entity.vo.CoursePublishVo;
import com.wlk.service.edu.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(courseId);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程发布
    @PostMapping("/publish/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");//设置课程发布状态！！
        courseService.updateById(course);
        return R.ok();
    }

    //课程列表 TODO 完善条件查询带分页
    @GetMapping("/getCourseList")
    public R getCourseList() {
        List<Course> list = courseService.list(null);
        return R.ok().data("items", list);
    }

    //删除课程
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

