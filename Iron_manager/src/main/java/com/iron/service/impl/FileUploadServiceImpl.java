package com.iron.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.iron.properties.MinioProperties;
import com.iron.properties.UserAuthProperties;
import com.iron.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    MinioProperties minioProperties;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {

            // 第一步：创建minio客户端
            final MinioClient minioClient = MinioClient.builder()
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                    .endpoint(minioProperties.getEndpointUrl())
                    .build();

            // 第二步：判断是否存在，不存在就进行创建
            final boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());

            if (!found) {

                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {

                System.out.println(minioProperties.getBucketName() + "已经存在！");
            }

            // 第三步骤：上传文件，并保证文件名称不重复
            final String curDate = DateUtil.format(new Date(), "yyyy-MM-dd");
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = curDate + "/" + uuid + multipartFile.getOriginalFilename();

            final PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .bucket(minioProperties.getBucketName())
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs);

            // 第四步：返回minio服务器此文件的地址给前端,让前端进行访问
            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}
