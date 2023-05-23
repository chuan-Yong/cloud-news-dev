package com.cloud.service.api.controller.file;

import com.cloud.bo.NewAdminBO;
import com.cloud.grace.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 99141
 */
@Api(value = "文件上传controller",tags = {"文件上传controller"})
@RequestMapping("fs")
public interface FileUploadControllerApi {


    @ApiOperation(value = "用户上传头像", notes = "用户上传头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    GraceJSONResult fileUpload(@RequestParam String userId, MultipartFile file) throws Exception;

    /** 上传多个文件
     * @param userId
     * @param files
     * @return
             * @throws Exception
     */
    @PostMapping("/uploadSomeFiles")
    GraceJSONResult uploadSomeFiles(@RequestParam String userId,MultipartFile[] files) throws Exception;


    @ApiOperation(value = "用户上传人脸信息", notes = "用户上传人脸信息", httpMethod = "POST")
    @PostMapping("/uploadToGridFS")
    GraceJSONResult uploadToGridFs(@RequestBody NewAdminBO newAdminBO) throws Exception;

    @ApiOperation(value = "查看上传人脸信息", notes = "查看上传人脸信息", httpMethod = "GET")
    @GetMapping("/readInGridFS")
    void readInGridFS(String faceId,
                      HttpServletRequest request,
                      HttpServletResponse response) throws Exception;
}
