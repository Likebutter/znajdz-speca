package com.example.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping(value = "/tags")
    public ResponseEntity<List<Tag>> getTags() {

        List<Tag> response = tagRepository.findAll();

        return new ResponseEntity<List<Tag>>(response, HttpStatus.OK);
    }
}
