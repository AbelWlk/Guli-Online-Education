package com.wlk.service.ucenter.api;

import com.google.gson.Gson;
import com.wlk.common.utils.JwtUtils;
import com.wlk.common.utils.R;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.ucenter.entity.Member;
import com.wlk.service.ucenter.service.MemberService;
import com.wlk.service.ucenter.util.ConstantWxUtil;
import com.wlk.service.ucenter.util.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@RequestMapping("/api/ucenter/wx")
@Controller
public class WxApiController {

    @Resource
    private MemberService memberService;


    @GetMapping("/callback")
    public String callback(String code, String state) {

        System.out.println(code);
        System.out.println(state);

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtil.WX_OPEN_APP_ID,
                ConstantWxUtil.WX_OPEN_APP_SECRET,
                code);

        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        //查询数据库当前用用户是否曾经使用过微信登录
        Member member = memberService.getByOpenid(openid);
        if (member == null) {
            System.out.println("新用户注册");

            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new GuliException(20001, "获取用户信息失败");
            }

            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String headimgurl = (String) mapUserInfo.get("headimgurl");

            //向数据库中插入一条记录
            member = new Member();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(headimgurl);
            memberService.save(member);
        }

        //TODO 登录
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());


        return "redirect:http://localhost:3000?token=" + jwtToken;
    }

    @GetMapping("/login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantWxUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "wlk"
        );

        return "redirect:" + url;
    }
}
