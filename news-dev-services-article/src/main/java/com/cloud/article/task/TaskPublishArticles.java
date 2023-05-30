package com.cloud.article.task;

import com.cloud.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 0:32 2023/5/25
 * @Modified by:ycy
 */

@Configuration
//@EnableScheduling
public class TaskPublishArticles {

    @Autowired
    private ArticleService articleService;

    // 添加定时任务，注明定时任务的表达式
    @Scheduled(cron = "0/3 * * * * ?")
    private void publishArticles() {
        //调用文章service，把当前时间应该发布的定时文章，状态改为即时
        articleService.updateAppointToPublish();
    }
}
