package com.cloud.grace.result.exception;

import com.cloud.grace.result.ResponseStatusEnum;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:49 2021/5/7
 * @Modified by:ycy
 */
public class CustomException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public CustomException(ResponseStatusEnum responseStatusEnum) {
        super("当前具体异常状态码为:" + responseStatusEnum.status() + "，具体异常信息为:"
        + responseStatusEnum.msg());
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
