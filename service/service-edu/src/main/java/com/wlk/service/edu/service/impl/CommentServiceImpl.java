package com.wlk.service.edu.service.impl;

import com.wlk.service.edu.entity.Comment;
import com.wlk.service.edu.mapper.CommentMapper;
import com.wlk.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
