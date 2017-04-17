package com.example.Photo;


import com.example.AWS.S3Util;
import com.example.Company.Company;
import com.example.Job.Job;
import com.example.Opinion.Opinion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotoDao {

    @Autowired
    private S3Util s3Util;

    @Autowired
    private PhotoRepository photoRepository;

    private Integer keyValue;

    public PhotoDao() {
        keyValue = 101;
    }

    public void savePhotos(ServerPhotoRequest request) {

        PhotoInsertThread thread = new PhotoInsertThread(keyValue, photoRepository, s3Util, request);
        thread.start();
        keyValue += request.getImages().size();
    }
}
