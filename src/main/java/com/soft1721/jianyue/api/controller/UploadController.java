package com.soft1721.jianyue.api.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.soft1721.jianyue.api.util.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class UploadController {
    @PostMapping("/avatar/upload")
    public String ossUpload(@RequestParam("file") MultipartFile sourceFile) {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAIBFVnODRHEjCL";
        String accessKeySecret = "Bk8yEz8F9UnPqAk5SbFQMRkif5X6fv";
        String bucketName = "niit-soft1721-25";
        String filedir = "avatar/";
        // 获取文件名
        String fileName = sourceFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //uuid生成主文件名
        String prefix = UUID.randomUUID().toString();
        String newFileName = prefix + suffix;
        File tempFile = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(prefix, prefix);
            // MultipartFile to File
            sourceFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filedir + newFileName, tempFile);
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + newFileName, expiration);
        ossClient.shutdown();
        return url.toString();
    }
}