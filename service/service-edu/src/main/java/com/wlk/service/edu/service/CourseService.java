package com.wlk.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wlk.service.edu.entity.frontvo.CourseFrontVo;
import com.wlk.service.edu.entity.frontvo.CourseWebVo;
import com.wlk.service.edu.entity.vo.CourseInfoVo;
import com.wlk.service.edu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String courseId);

    void removeCourse(String courseId);

    Map<String, Object> getCourseList(Page<Course> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseBaseInfo(String id);
}
