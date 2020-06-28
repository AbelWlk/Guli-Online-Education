package com.wlk.service.msm.controller;

import com.wlk.common.utils.R;
import com.wlk.common.utils.RandomUtil;
import com.wlk.service.msm.service.MsmService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RequestMapping("/msm")
@RestController
public class MsmApiController {

    @Resource
    private MsmService msmService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        //从redis获取验证码，获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //生成6为随机验证码
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        Boolean flag = msmService.send(param, phone);

        if (flag) {
            //发送成功 放大redis中
            //设置有效时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败！");
        }
    }

}
