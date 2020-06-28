package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.client.VodClient;
import com.wlk.service.edu.entity.Video;
import com.wlk.service.edu.service.VideoService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    @Resource
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video) {
        videoService.save(video);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable String videoId) {
        Video video = videoService.getById(videoId);

        if (!StringUtils.isEmpty(video.getVideoSourceId())) {
            R result = vodClient.removeAliVideo(video.getVideoSourceId());
            if (result.getCode() == 20001) {
                throw new GuliException(ResultCode.ERROR, "熔断器！！");
            }
        }

        videoService.removeById(videoId);
        return R.ok();
    }


    //修改小节 TODO
    @PostMapping("/updateVideo")
    public R removeVideo(@RequestBody Video video) {
        videoService.updateById(video);
        return R.ok();
    }


    //查询小节
    @GetMapping("/getVideo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId) {
        Video byId = videoService.getById(videoId);
        return R.ok().data("videoInfo", byId);
    }

}

