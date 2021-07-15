package com.cloud.grace.result.exception;

import com.cloud.grace.result.ResponseStatusEnum;

/**
 * @Author: ycy
 * @Description:自定义异常
 * @Date:Create in 22:47 2021/5/7
 * @Modified by:ycy
 */
public class GraceException {

    public static void display(ResponseStatusEnum responseStatusEnum) {
        throw new CustomException(responseStatusEnum);
    }
}
