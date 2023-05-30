package com.cloud.html.controller;

import com.cloud.service.api.BaseController;
import com.cloud.service.api.controller.article.ArticleHTMLControllerApi;
import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 23:58 2023/5/23
 * @Modified by:ycy
 */
@RestController
public class ArticleHtmlController extends BaseController implements ArticleHTMLControllerApi {

    final static Logger logger = LoggerFactory.getLogger(ArticleHtmlController.class);

    @Autowired
    private GridFSBucket gridFSBucket;

    @Value("${freemarker.html.article}")
    private String articlePath;

    @Override
    public Integer download(String articleId, String articleMongoId)
            throws Exception {

        // 拼接最终文件的保存的地址
        String path = articlePath + File.separator + articleId + ".html";

        System.out.println("保存地址为：" + path);

        // 获取文件流，定义存放的位置和名称
        File file = new File(path);
        // 创建输出流
        OutputStream outputStream = new FileOutputStream(file);
        // 执行下载
        gridFSBucket.downloadToStream(new ObjectId(articleMongoId), outputStream);

        return HttpStatus.OK.value();
    }

    @Override
    public Integer delete(String articleId) throws Exception {

        // 拼接最终文件的保存的地址
        String path = articlePath + File.separator + articleId + ".html";
        // 获取文件流，定义存放的位置和名称
        File file = new File(path);
        // 删除文件
        file.delete();

        return HttpStatus.OK.value();
    }
}
