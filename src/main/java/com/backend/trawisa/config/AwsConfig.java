package com.backend.trawisa.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.app.base.project.utils.Print;
import com.backend.trawisa.security.properties.ConfigProperties;
import com.backend.trawisa.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


@Configuration
public class AwsConfig {


    private final ConfigProperties properties;

    @Autowired
    public AwsConfig(ConfigProperties properties) {
        this.properties = properties;
    }

    private AWSCredentials awsCredentials(String accessKey, String secretKey) {
        Print.log("Aws AccesKey " + accessKey + " Secret Key " + secretKey);
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return awsCredentials;
    }

    private AmazonS3 amazonS3ClientBuilder() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(properties.getAwsAccessKey(), properties.getAwsSecretKey())))
                .withRegion(properties.getAwsRegion()).build();
        return s3Client;
    }


    private void createBucket(String bucketName) {
        AmazonS3 s3client = amazonS3ClientBuilder();
        if (s3client.doesBucketExistV2(bucketName)) {
            Print.log("Bucket {} already exists");
            return;
        }
        try {
            Print.log("Creating bucket {}");

            s3client.createBucket(bucketName);
        } catch (Exception e) {
            Print.log("Exception while Creating bucket {} " + e.getMessage());
            e.printStackTrace();

        }
    }


    public void uploadFileToS3(String fileName, byte[] fileBytes, String bucket) {
        AmazonS3 s3client = amazonS3ClientBuilder();
        File file = new File(fileName);

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(fileBytes);
        } catch (FileNotFoundException e) {
            Print.log("uploadFileToS3 FileNot found");
            e.printStackTrace();
        } catch (IOException e) {
            Print.log("uploadFileToS3 IOException");
            e.printStackTrace();
        }

        s3client.putObject(bucket, fileName, file);
    }

    public void uploadFileToBucket(String fileName, File file, String folderToUpload) {
        AmazonS3 s3client = amazonS3ClientBuilder();

        // Create the folder (prefix) in S3 if it doesn't exist
        if (!s3client.doesObjectExist(properties.getAwsBucket(), folderToUpload + "/")) {
            s3client.putObject(properties.getAwsBucket(), folderToUpload + "/", "");
        }

        // Upload the file with the updated key including the "folder"
        String updatedFileName = folderToUpload + "/" + fileName;
        Print.log("Uploading file to " + updatedFileName);
//        s3client.putObject(properties.awsBucket(), updatedFileName, file);
        s3client.putObject(new PutObjectRequest(properties.getAwsBucket(), updatedFileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


    public void uploadFileAsStreamToBucket(String fileName, MultipartFile file, String folderToUpload) {
        AmazonS3 s3client = amazonS3ClientBuilder();

        // Create the folder (prefix) in S3 if it doesn't exist
        if (!s3client.doesObjectExist(properties.getAwsBucket(), folderToUpload + "/")) {
            s3client.putObject(properties.getAwsBucket(), folderToUpload + "/", "");
        }

        // Upload the file with the updated key including the "folder"
        String updatedFileName = folderToUpload + "/" + fileName;
        Print.log("Uploading file to " + updatedFileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        s3client.putObject(new PutObjectRequest(properties.getAwsBucket(), updatedFileName, FileUtils.getByteInputStream(file),metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void deleteFileFromBucket(String filename) {

        Print.log("filename : "+filename);
        AmazonS3 s3client = amazonS3ClientBuilder();
        if (s3client.doesObjectExist(properties.getAwsBucket(), filename)) {

            DeleteObjectRequest delObjReq = new DeleteObjectRequest(properties.getAwsBucket(), filename);
            s3client.deleteObject(delObjReq);
            Print.log("File deleted successfully");
        } else {
            Print.log("File doesnot exist");
        }
    }

    public void deleteMultipleFilesFromBucket(List<String> files) {
        AmazonS3 s3client = amazonS3ClientBuilder();
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(properties.getAwsBucket())
                .withKeys(files.toArray(new String[0]));
        Print.log("Deleting files...");
        s3client.deleteObjects(delObjReq);
    }

/*    public File getFileFromBucket(String filename, String folderName) {
        InputStream inputStream = getFileInputStreamS3(filename, folderName);
        File file = new File(filename);
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return file;
        }
        return file;
    }*/

    public InputStream getFileInputStreamS3(String filename, String folderName) {
        AmazonS3 s3client = amazonS3ClientBuilder();
        S3Object s3object = s3client.getObject(properties.getAwsBucket(), folderName + "/" + filename);
        return s3object.getObjectContent();
    }


}
