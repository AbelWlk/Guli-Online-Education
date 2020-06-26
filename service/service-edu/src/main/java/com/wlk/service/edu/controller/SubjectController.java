package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.service.SubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-25
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    //添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {

        //得到上传的文件
        subjectService.saveSubject(file,subjectService);

        return R.ok();
    }

}

