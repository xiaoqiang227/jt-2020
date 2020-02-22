package com.jt.controller;

import com.jt.serviceImpl.FileService;
import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/pic/upload")
    public ImageVO fileUpload(MultipartFile uploadFile){
        return fileService.upload(uploadFile);
    }
}
