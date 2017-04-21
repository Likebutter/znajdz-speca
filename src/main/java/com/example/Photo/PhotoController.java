package com.example.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping(value = "/photo/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable Integer id) {

        Photo photo = photoRepository.findOne(id);

        if(photo == null)
            return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<Photo>(photo, HttpStatus.OK);
    }
}
