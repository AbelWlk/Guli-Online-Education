package com.wlk.service.oss.controller;


import com.wlk.common.utils.R;
import com.wlk.service.oss.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RequestMapping("/oss/file")
@RestController
public class OssController {

    @Resource
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //返回上传文件路径
        String url = ossService.upload(file);
        return R.ok().data("url",url);
    }
}
