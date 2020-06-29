package com.wlk.service.ucenter.service;

import com.wlk.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wlk.service.ucenter.entity.vo.LoginVo;
import com.wlk.service.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    Member getByOpenid(String openid);
}
