package com.wlk.service.edu.service;

import com.wlk.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wlk.service.edu.entity.vo.CourseInfoVo;

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
}
