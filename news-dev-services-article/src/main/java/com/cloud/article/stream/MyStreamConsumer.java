package com.cloud.article.stream;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:23 2023/5/26
 * @Modified by:ycy
 */

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 构建消费端
 */
@Component
@EnableBinding(MyStreamChannel.class)
public class MyStreamConsumer {

    @StreamListener(MyStreamChannel.INPUT)
    public void receiveMsg(String dumpling) {
        System.out.println(dumpling);
    }
}
