package com.wlk.service.ucenter.mapper;

import com.wlk.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author wlk
 * @since 2020-06-28
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer selectRegisterCount(String day);
}
