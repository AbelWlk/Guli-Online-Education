package com.wlk.service.edu.client;

import com.wlk.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //根据视频id删除视频
    @DeleteMapping("/vod/video/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable("id") String id);

    @DeleteMapping("/vod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
