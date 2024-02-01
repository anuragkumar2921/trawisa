package com.backend.trawisa.service;


import com.backend.trawisa.config.AwsConfig;
import com.backend.trawisa.service_listener.FileServiceListener;
import com.backend.trawisa.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileServiceListener {

    private final AwsConfig awsUtils;

    @Autowired
    public FileServiceImpl(AwsConfig awsUtils) {
        this.awsUtils = awsUtils;
    }

    @Override
    public String uploadFileToS3(MultipartFile multipartFile, String folder) {
        String fileName = FileUtils.generateFileName(multipartFile);
        this.awsUtils.uploadFileAsStreamToBucket(fileName, multipartFile, folder);
        return folder + "/" + fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;

        InputStream filePath = new FileInputStream(fullPath);
        return filePath;
    }

    @Override
    public void deleteFileFromS3(String fileName) {
        this.awsUtils.deleteFileFromBucket(fileName);
    }
}
