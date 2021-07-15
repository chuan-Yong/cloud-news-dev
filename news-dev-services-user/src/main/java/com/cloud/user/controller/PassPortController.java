package com.cloud.user.controller;

import com.cloud.bo.RegistLoginBo;
import com.cloud.entity.AppUser;
import com.cloud.enums.UserStatus;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.service.api.BaseController;
import com.cloud.service.api.PassPortControllerApi;
import com.cloud.user.service.UserService;
import com.cloud.utils.IPUtil;
import com.cloud.utils.InformationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

/**
 * @author 99141
 */
@RestController
public class PassPortController extends BaseController implements PassPortControllerApi {

    @Autowired
    private InformationUtils informationUtils;

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getSMSCode(String mobile, HttpServletRequest servletRequest) {
        //获取用户ip;
        String userIp = IPUtil.getRequestIp(servletRequest);
        //设置请求时间为60s；
        redisOperator.setnx60s(MOBILE_SMSCODE + ":" + userIp,userIp);
        //String code = "123456";
        String random = (int)((Math.random() * 9 + 1) * 100000) + "";
        try {
            informationUtils.sendMessage("17779740732",random);
            //把验证码存入redis中;
            redisOperator.set(MOBILE_SMSCODE + ":" +mobile,random);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GraceJSONResult.ok();
    }

    @Override
    public GraceJSONResult doLogin(@Valid RegistLoginBo registLoginBo,
                                   BindingResult bindingResult,
                                   HttpServletRequest servletRequest,
                                   HttpServletResponse servletResponse) {
        //判断bindingResult是否存在错误信息，如果存在则需要返回
        if(bindingResult.hasErrors()) {
            Map<String, Object> errorsMap = getErrors(bindingResult);
            return GraceJSONResult.errorMap(errorsMap);
        }
        //判断验证码是否匹配
        String smsCode = registLoginBo.getSmsCode();
        String mobile = registLoginBo.getMobile();
        String redisSmsCode = redisOperator.get(MOBILE_SMSCODE + ":" + mobile);
        if (StringUtils.isBlank(redisSmsCode)|| (!redisSmsCode.equalsIgnoreCase(smsCode))) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        //判断用户是否已经存在;
        AppUser appUser = userService.queryMobileIsExist(mobile);
        if(appUser != null && appUser.getActiveStatus().equals(UserStatus.FROZEN.type)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        } else  if (appUser == null) {
            userService.createUser(mobile);
        }

        //设置分布式cookie
        assert appUser != null;
        Integer activeStatus = appUser.getActiveStatus();
        if(!activeStatus.equals(UserStatus.FROZEN.type)) {
            String uToken = UUID.randomUUID().toString();
            redisOperator.set(REDIS_USER_TOKEN + ":" + appUser.getId(),uToken);
            setCookie(servletRequest,servletResponse,"utoken",uToken,REDIS_MAXAGE);
            setCookie(servletRequest,servletResponse,"uid",appUser.getId(),REDIS_MAXAGE);
        }

        //用户登录或者注册成功删除redis中的验证码信息，只允许使用一次
        redisOperator.del(MOBILE_SMSCODE + ":" + mobile);
        return GraceJSONResult.ok(activeStatus);
    }

}
