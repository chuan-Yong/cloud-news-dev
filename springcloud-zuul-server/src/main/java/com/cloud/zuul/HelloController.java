package com.cloud.zuul;

import com.cloud.service.api.controller.user.HelloControllerApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 10:19 2023/5/31
 * @Modified by:ycy
 */
@RestController
public class HelloController implements HelloControllerApi {


    @Override
    public Object hello() {
        return "666666666-----";
    }

    @Override
    public Object redis() {
        return null;
    }
}
