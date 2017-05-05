package com.edu.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFile {
    public static final String BASE_URL = "/Users/hanhao0125/Documents/edu-resources/";
    public static String uploadFile(MultipartFile multipartFile,String path) {
        //相对路径,数据库存储该字段
        String relativePath = path + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        //绝对路径,文件存放于本地的路径
        String absolutePath = UploadFile.BASE_URL + relativePath;

        File file = new File(absolutePath);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return relativePath;
    }
}
