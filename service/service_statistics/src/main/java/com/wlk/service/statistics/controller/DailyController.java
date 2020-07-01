package com.wlk.service.statistics.controller;


import com.wlk.common.utils.R;
import com.wlk.service.statistics.client.UcenterClient;
import com.wlk.service.statistics.service.DailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-30
 */
@CrossOrigin
@RestController
@RequestMapping("/statistics/daily")
public class DailyController {

    @Resource
    private DailyService dailyService;

    @PostMapping("registerCount/{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end){
        Map<String,Object> map=dailyService.getShowData(type,begin,end);

        return R.ok().data(map);
    }
}

