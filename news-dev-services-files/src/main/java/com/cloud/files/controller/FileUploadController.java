package com.cloud.files.controller;

import com.cloud.bo.NewAdminBO;
import com.cloud.files.FileResources;
import com.cloud.files.service.UploadService;
import com.cloud.grace.result.GraceJSONResult;
import com.cloud.grace.result.ResponseStatusEnum;
import com.cloud.grace.result.exception.GraceException;
import com.cloud.service.api.controller.file.FileUploadControllerApi;
import com.cloud.utils.FileUtils;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 22:21 2022/9/21
 * @Modified by:ycy
 */
@RestController
public class FileUploadController implements FileUploadControllerApi {

    final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private UploadService uploadService;

    @Autowired
    private FileResources fileResources;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Override
    public GraceJSONResult fileUpload(String userId, MultipartFile file) throws Exception {
        String path = "";
        if (!StringUtils.isBlank(userId)&&!StringUtils.equalsIgnoreCase(userId,"undefined")) {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    String fileNameAtrr[] = fileName.split("\\.");
                    String suffix = fileNameAtrr[fileNameAtrr.length - 1];
                    //判断文件名后缀
                    if (!suffix.equalsIgnoreCase("png")
                            && !suffix.equalsIgnoreCase("jpg")
                            && !suffix.equalsIgnoreCase("jpeg")) {
                        return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_FORMATTER_FAILD);
                    }
                    //上传
                    path = uploadService.uploadFdfs(file, suffix);
                } else {
                    return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_NULL_ERROR);
                }
            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
            }

            logger.info("path：" + path);
            String finalPath = "";
            if(StringUtils.isNotBlank(path)) {
                //http://172.20.70.7:8888/group1/M00/00/00/rBRGB2NKW2OAOK0OAADtbdUeb6o140.jpg
                finalPath = fileResources.getHost() + path;
            } else {
                return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
            }
            return GraceJSONResult.ok(finalPath);
        } else {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }
    }

    @Override
    public GraceJSONResult uploadSomeFiles(String userId, MultipartFile[] files) throws Exception {
        // 声明list，用于存放多个图片的地址路径，返回到前端
        List<String> imageUrlList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                String path = "";
                if (file != null) {
                    // 获得文件上传的名称
                    String fileName = file.getOriginalFilename();

                    // 判断文件名不能为空
                    if (StringUtils.isNotBlank(fileName)) {
                        String fileNameArr[] = fileName.split("\\.");
                        // 获得后缀
                        String suffix = fileNameArr[fileNameArr.length - 1];
                        // 判断后缀符合我们的预定义规范
                        if (!suffix.equalsIgnoreCase("png") &&
                                !suffix.equalsIgnoreCase("jpg") &&
                                !suffix.equalsIgnoreCase("jpeg")
                        ) {
                            continue;
                        }
                        // 执行上传
                        path = uploadService.uploadFdfs(file,suffix);

                    } else {
                        continue;
                    }
                } else {
                    continue;
                }

                String finalPath = "";
                if (StringUtils.isNotBlank(path)) {
                    finalPath = fileResources.getHost() + path;
                    //finalPath = fileResource.getOssHost() + path;
                    // FIXME: 放入到imagelist之前，需要对图片做一次审核
                    imageUrlList.add(finalPath);
                } else {
                    continue;
                }
            }
        }
        return GraceJSONResult.ok(imageUrlList);
    }

    @Override
    public GraceJSONResult uploadToGridFs(NewAdminBO newAdminBO) throws Exception {
        //获取图片的Base64字符串
        String file64 = newAdminBO.getImg64();
        byte[] bytes = new BASE64Decoder().decodeBuffer(file64.trim());

        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        //返回gridfs主键
        ObjectId fileId = gridFSBucket.uploadFromStream(newAdminBO.getUsername() + ".png", inputStream);

        return GraceJSONResult.ok(fileId.toString());
    }

    @Override
    public void readInGridFS(String faceId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(faceId) || StringUtils.equalsIgnoreCase(faceId,null)) {
            GraceException.display(ResponseStatusEnum.ADMIN_FACE_NULL_ERROR);
        }

        File adminFaceFile = readGridFSByFaceId(faceId);

        // 2. 把人脸图片输出到浏览器
        FileUtils.downloadFileByStream(response, adminFaceFile);
    }

    private File readGridFSByFaceId(String faceId) throws Exception {

        GridFSFindIterable gridFSFiles
                = gridFSBucket.find(Filters.eq("_id", new ObjectId(faceId)));

        GridFSFile gridFS = gridFSFiles.first();

        if (gridFS == null) {
            GraceException.display(ResponseStatusEnum.FILE_NOT_EXIST_ERROR);
        }

        String fileName = gridFS.getFilename();
        System.out.println(fileName);

        // 获取文件流，保存文件到本地或者服务器的临时目录
        File fileTemp = new File("/workspace/temp_face");
        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }

        File myFile = new File("/workspace/temp_face/" + fileName);

        // 创建文件输出流
        OutputStream os = new FileOutputStream(myFile);
        // 下载到服务器或者本地
        gridFSBucket.downloadToStream(new ObjectId(faceId), os);

        return myFile;
    }

}
