package com.backend.trawisa.service_listener;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServiceListener {
    String uploadFileToS3(MultipartFile multipartFile,String folderName) throws IOException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;
    void deleteFileFromS3(String fileName);

}
