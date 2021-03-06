package com.wlk.service.edu.controller;

import com.wlk.common.utils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin//跨域解决
@RequestMapping("/edu/user")
@RestController
public class EduLoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name","admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
