package com.example.Photo;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.AWS.S3Util;
import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class PhotoInsertThread extends Thread{

        private Integer keyValue;
        private PhotoRepository photoRepository;
        private S3Util s3Util;
        private ServerPhotoRequest request;

    public PhotoInsertThread(Integer keyValue, PhotoRepository photoRepository, S3Util s3Util, ServerPhotoRequest request) {
        this.keyValue = keyValue;
        this.photoRepository = photoRepository;
        this.s3Util = s3Util;
        this.request = request;
    }

    public void run() {

        saveImagesToDatabase();
    }

    public void saveImagesToDatabase() {

        for(FileData image : request.getImages()) {
            String uploadKey = buildUploadKey(image);
            Boolean uploadSucceeded = storeInCloud(image, uploadKey);
            Photo photo;

            if(uploadSucceeded) {
                photo = buildPhoto(uploadKey);
                photoRepository.save(photo);
            }
        }
    }

    private String buildUploadKey(FileData file) {

        String extension = file.getExtension();
        String uploadKey = "znajdzspeca/images/" + keyValue + "." + extension;
        keyValue++;

        return uploadKey;
    }

    private Boolean storeInCloud(FileData file, String uploadKey) {

        PutObjectResult putObjectResult;

        try {
            putObjectResult = s3Util.upload(file.getContent(), uploadKey);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Udało się");
        return true;
    }

    private Photo buildPhoto(String uploadKey) {

        Photo photo = new Photo();
        photo.setPhotoURL("https://s3-eu-west-1.amazonaws.com/pzprojektbucket/" + uploadKey);
        photo.setJob(request.getJob());
        photo.setOpinion(request.getOpinion());
        photo.setCompany(request.getCompany());

        return photo;
    }
}
