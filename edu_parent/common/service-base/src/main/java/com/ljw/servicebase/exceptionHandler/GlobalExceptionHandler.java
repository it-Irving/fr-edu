package com.ljw.servicebase.exceptionHandler;

import com.ljw.commonutils.ExceptionUtil;
import com.ljw.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理程序
 *
 * @author Luo
 */
// @ControllerAdvice 配合 @ExceptionHandler 实现全局异常处理
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)      //指定错误类型

    @ResponseBody       //不在Controller中，没有@RestController。用来响应json数据
    public R error(Exception e){
        e.printStackTrace();
        //输出详细日志堆栈到文件中
//        log.error(ExceptionUtil.getMessage(e));
        log.error(e.getMessage());
        return R.error().message("执行全局异常。。。。。。。");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(){

        return R.error().message("ArithmeticException执行自定义异常");
    }


    /**
     * 自定义异常
     *
     * @param e e
     * @return {@link R}
     */
    @ExceptionHandler(LjwException.class)
    @ResponseBody
    public R error(LjwException e){
        return R.error().message(e.getMsg()).code(e.getCode());
    }


}
