package com.cloud.article.stream;

import com.cloud.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:12 2023/5/26
 * @Modified by:ycy
 */
@Component
@EnableBinding(MyStreamChannel.class)
public class StreamServiceImpl implements StreamService{

    @Autowired
    private MyStreamChannel myStreamChannel;

    @Override
    public void sendMsg() {
        AppUser user = new AppUser();
        user.setId("10101");
        user.setNickname("imooc");

        // 消息通过绑定器发送给mq
        myStreamChannel.output()
                .send(MessageBuilder.withPayload(user).build());
    }

    @Override
    public void eat(String dumpling) {
        myStreamChannel.output()
                .send(MessageBuilder.withPayload(dumpling).build());
    }
}
