package com.backend.trawisa.utils;

import com.app.base.project.utils.Print;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class FileUtils {

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static String generateFileName(MultipartFile multiPartFile) {
        //Full path
        return  System.currentTimeMillis()+"_"+multiPartFile.getOriginalFilename();
    }

    public File convertBase64ToFile(String base64Content, String filename) {
        byte[] decodedContent = Base64.getDecoder().decode(base64Content.getBytes(StandardCharsets.UTF_8));
        return bytesToFile(decodedContent, filename);
    }

    public File bytesToFile(byte[] content, String fileName) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        } catch (IOException e) {
            return null;
        }
        return file;
    }

    public File convertMultipartFileToFile(MultipartFile multipartFile) {

        // Get the bytes from the MultipartFile
        File convertedFile = new File(multipartFile.getOriginalFilename());
        // Write the bytes to the File
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            byte[] fileBytes = multipartFile.getBytes();
            // Create a new File
            fos.write(fileBytes);
        } catch (FileNotFoundException e) {
            Print.log("Aws Utils FileNotFoundException " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Print.log("Aws Utils IOException " + e.getMessage());
            e.printStackTrace();
        }

        return convertedFile;
    }



    public static ByteArrayInputStream getByteInputStream(MultipartFile multipartFile){
        try {

            return new ByteArrayInputStream(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
