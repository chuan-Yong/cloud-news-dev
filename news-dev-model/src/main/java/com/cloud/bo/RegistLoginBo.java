package com.cloud.bo;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:41 2021/6/19
 * @Modified by:ycy
 */
public class RegistLoginBo implements Serializable{

    private static final long serialVersionUID = 2242012046615264582L;

    @NotBlank(message = "手机号码不能为空")
    private  String mobile;

    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
