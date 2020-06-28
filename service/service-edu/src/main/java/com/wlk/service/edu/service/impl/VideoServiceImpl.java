package com.wlk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.service.edu.client.VodClient;
import com.wlk.service.edu.entity.Video;
import com.wlk.service.edu.mapper.VideoMapper;
import com.wlk.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VideoMapper videoMapper;
    @Resource
    private VodClient vodClient;

    //todo 删除小节同时删除视频文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        //查出所有视频id
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        List<Video> videos = videoMapper.selectList(wrapperVideo);

        List<String> videoIds = new ArrayList<>();
        for (Video video : videos) {
            videoIds.add(video.getVideoSourceId());
        }

        if (videoIds.size() > 0) {
            vodClient.deleteBatch(videoIds);
        }


        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        videoMapper.delete(wrapper);
    }
}
