package com.cloud.grace.result.exception;

import com.cloud.grace.result.GraceJSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: ycy
 * @Description:统一异常拦截处理
 * @Date:Create in 23:08 2021/5/7
 * @Modified by:ycy
 */
@ControllerAdvice
public class GraceExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public GraceJSONResult returnMyException(CustomException exception){
        exception.printStackTrace();
        return GraceJSONResult.exception(exception.getResponseStatusEnum());
    }
}
