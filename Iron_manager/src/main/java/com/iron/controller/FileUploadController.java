package com.iron.controller;

import com.iron.model.Result.Result;
import com.iron.model.Result.ResultCodeEnum;
import com.iron.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="通过mino实现文件的上传")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    /**
     * 接受前端传动过来的数据，返回对应minio服务器里面的对应的rul地址类似于：localhost:90000/ironzx-bucket/1.png
     * @return
     */
    @Operation(summary = "上传数据")
    @GetMapping("/fileUpload")
    public Result fileUploiad(@RequestParam("file")MultipartFile multipartFile) {

        String fileUrl = fileUploadService.fileUpload(multipartFile);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
