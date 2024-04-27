package com.iron;


import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class test {

    public static void main(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        // 第一步创建一个minio客户端
        final MinioClient minioCLien = MinioClient.builder()
                .endpoint("http://127.0.0.1:9000")
                .credentials("minioadmin", "minioadmin")
                .build();

        // 第二步：查询对应的bucket是否存在
        final boolean found = minioCLien.bucketExists(BucketExistsArgs.builder().bucket("ironzx-bucket").build());

        if (!found) {

            minioCLien.makeBucket(MakeBucketArgs.builder().bucket("ironzx-bucket").build());
        } else {

            System.out.println("ironzx-bucket已经创建！");
        }

        // 第三步：将文件传入到服务器中
        final FileInputStream stream = new FileInputStream("C:\\Users\\Iron\\Pictures\\2019102902001726050.jpg!cc0.cn.jpg");
        final PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket("ironzx-bucket")
                .stream(stream, stream.available(), -1)
                .object("2019102902001726050.jpg!cc0.cn.jpg")
                .build();
        minioCLien.putObject(putObjectArgs);

        // 第四步：构建fileURL,即输出路径
        final String fileURl = "http://127.0.0.1：9000/ironzx-bucket/2019102902001726050.jpg!cc0.cn.jpg";
        System.out.println(fileURl);
    }
}
