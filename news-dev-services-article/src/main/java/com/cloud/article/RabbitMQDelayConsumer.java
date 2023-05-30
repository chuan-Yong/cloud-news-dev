package com.cloud.article;

import com.cloud.article.service.ArticleService;
import com.cloud.service.config.RabbitMQDelayConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 16:30 2023/5/26
 * @Modified by:ycy
 */
@Component
public class RabbitMQDelayConsumer {

    @Autowired
    private ArticleService articleService;

    @RabbitListener(queues = {RabbitMQDelayConfig.QUEUE_DELAY})
    public void watchQueue(String payload, Message message) {
        System.out.println(payload);

        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        System.out.println(routingKey);

        System.out.println("消费者接受的延迟消息：" + new Date());

        // 消费者接收到定时发布的延迟消息，修改当前的文章状态为`即时发布`
        String articleId = payload;
        articleService.updateArticleToPublish(articleId);
    }
}
