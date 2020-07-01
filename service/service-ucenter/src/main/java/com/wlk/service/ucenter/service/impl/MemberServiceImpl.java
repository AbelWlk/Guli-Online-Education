package com.wlk.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.JwtUtils;
import com.wlk.common.utils.MD5;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.ucenter.entity.Member;
import com.wlk.service.ucenter.entity.vo.LoginVo;
import com.wlk.service.ucenter.entity.vo.RegisterVo;
import com.wlk.service.ucenter.mapper.MemberMapper;
import com.wlk.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "信息填写不完整");
        }

        //获取会员
        Member member = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if (null == member) {
            throw new GuliException(20001, "用户不存在");
        }

        //校验密码
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001, "密码错误");
        }

        //校验是否被禁用
        if (member.getIsDisabled()) {
            throw new GuliException(20001, "该账号被禁用");
        }

        //使用JWT生成token字符串
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "信息填写不完整");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobleCode)) {
            throw new GuliException(20001, "验证码错误");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile", mobile));
        if (count.intValue() > 0) {
            throw new GuliException(20001, "账号已存在");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        this.save(member);
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        Member member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
