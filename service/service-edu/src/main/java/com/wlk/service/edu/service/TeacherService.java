package com.wlk.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-24
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher);
}
