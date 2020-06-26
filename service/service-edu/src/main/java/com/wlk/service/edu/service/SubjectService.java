package com.wlk.service.edu.service;

import com.wlk.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wlk.service.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-25
 */
public interface SubjectService extends IService<Subject> {

    //添加课程分裂
    void saveSubject(MultipartFile file,SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
