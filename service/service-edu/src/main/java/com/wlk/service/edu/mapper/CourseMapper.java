package com.wlk.service.edu.mapper;

import com.wlk.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlk.service.edu.entity.frontvo.CourseWebVo;
import com.wlk.service.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getCourseBaseInfo(String id);
}
