package com.wlk.service.ucenter.controller;


import com.wlk.common.utils.JwtUtils;
import com.wlk.common.utils.R;
import com.wlk.common.utils.ordervo.MemberOrder;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.ucenter.entity.Member;
import com.wlk.service.ucenter.entity.vo.LoginVo;
import com.wlk.service.ucenter.entity.vo.RegisterVo;
import com.wlk.service.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */

@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @GetMapping("/getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Member member = memberService.getById(memberId);
            return R.ok().data("userInfo", member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取信息失败");
        }
    }

    //根据用户id获取用户信息
    @PostMapping("/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable String id) {
        Member byId = memberService.getById(id);

        MemberOrder memberOrder = new MemberOrder();
        BeanUtils.copyProperties(byId, memberOrder);

        return memberOrder;
    }

    @GetMapping(value = "/countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

