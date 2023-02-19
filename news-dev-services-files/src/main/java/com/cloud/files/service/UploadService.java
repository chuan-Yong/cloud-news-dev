package com.cloud.files.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:18 2022/9/17
 * @Modified by:ycy
 */
public interface UploadService {

    public String uploadFdfs(MultipartFile file, String fileExtName) throws Exception;
}
