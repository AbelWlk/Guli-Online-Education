package com.wlk.service.base.exceptionhandler;

import com.wlk.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常！！");
    }

    //特定异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody//为了返回数据
    public R error(GuliException e){
        log.error(e.getMsg());
        e.printStackTrace();
        //return R.error().message("执行ArithmeticException异常！！");
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
