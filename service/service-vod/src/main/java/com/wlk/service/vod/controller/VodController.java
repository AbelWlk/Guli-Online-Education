package com.wlk.service.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.wlk.common.utils.R;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.vod.service.VodService;
import com.wlk.service.vod.utils.ConstantVodUtils;
import com.wlk.service.vod.utils.InitVodCilent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RequestMapping("/vod/video")
@RestController
public class VodController {

    @Resource
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAliVideo")
    public R uploadAliVideo(MultipartFile file) {

        String videoId = vodService.uploadVideo(file);

        return R.ok().data("videoSourceId", videoId);
    }

    //根据视频id删除视频
    @DeleteMapping("/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable String id) {

        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, "删除视频失败");
        }
    }

    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {

        vodService.removeVideoList(videoIdList);
        return R.ok();
    }

}
