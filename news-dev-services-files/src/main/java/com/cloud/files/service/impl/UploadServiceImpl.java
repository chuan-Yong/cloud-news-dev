package com.cloud.files.service.impl;

import com.cloud.files.service.UploadService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ycy
 * @Description:
 * @Date:Create in 21:19 2022/9/17
 * @Modified by:ycy
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    @Override
    public String uploadFdfs(@NotNull MultipartFile file, String fileExtName) throws Exception {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),
                                                                file.getSize(),
                                                                fileExtName,
                                                                null);

        return storePath.getFullPath();
    }
}
