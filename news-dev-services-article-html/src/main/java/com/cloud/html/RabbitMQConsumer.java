package com.cloud.html;

import com.cloud.html.controller.ArticleHTMLComponent;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:43 2023/5/26
 * @Modified by:ycy
 */
@Component
public class RabbitMQConsumer {

    @Autowired
    private ArticleHTMLComponent articleHTMLComponent;

    public void watchQueue(String payload, Message message) {
        //获取message中的路由key
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (routingKey.equalsIgnoreCase("article.publish.download.do")) {
            System.out.println("article.publish.download.do");

        } else if (routingKey.equalsIgnoreCase("article.success.do")) {
            System.out.println("article.success.do");

        } else if (routingKey.equalsIgnoreCase("article.download.do")) {
//            articleId + "," + articleMongoId
            String articleId = payload.split(",")[0];
            String articleMongoId = payload.split(",")[1];
            try {
                articleHTMLComponent.download(articleId, articleMongoId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (routingKey.equalsIgnoreCase("article.html.download.do")) {
            String articleId = payload;
            try {
                articleHTMLComponent.delete(articleId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不符合的规则：" + routingKey);
        }
    }

}
