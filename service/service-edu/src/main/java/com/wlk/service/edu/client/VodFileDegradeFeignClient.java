package com.wlk.service.edu.client;

import com.wlk.common.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //出错后执行
    @Override
    public R removeAliVideo(String id) {
        return R.error().message("删除视频出错了！");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了！");
    }
}
