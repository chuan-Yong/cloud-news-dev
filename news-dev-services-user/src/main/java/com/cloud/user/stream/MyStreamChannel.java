package com.cloud.user.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:13 2023/5/26
 * @Modified by:ycy
 */
@Component
public interface MyStreamChannel {

    String OUTPUT = "myOutput";

    String INPUT = "myInput";

    @Output(MyStreamChannel.OUTPUT)
    MessageChannel output();

    @Input(MyStreamChannel.INPUT)
    SubscribableChannel input();
}
