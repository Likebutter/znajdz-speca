package com.example.Photo;


import com.example.AWS.S3Util;
import org.springframework.beans.factory.annotation.Autowired;

public class PhotoDao {

    @Autowired
    private S3Util s3Util;

    @Autowired
    private PhotoRepository photoRepository;

    private Integer keyValue;

    public PhotoDao() {
        keyValue = 100;
    }

    public void savePhotos(ServerPhotoRequest request) {

        PhotoInsertThread thread = new PhotoInsertThread(keyValue, photoRepository, s3Util, request);
        thread.start();
        keyValue += request.getImages().size();
    }

}
