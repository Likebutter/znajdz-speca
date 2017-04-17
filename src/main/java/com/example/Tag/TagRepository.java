package com.example.Tag;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer>{

    List<Tag> findByNameIn(List<String> name);

    List<Tag> findByIdIn(List<Integer> id);

    Tag findByName(String name);

    Tag findById(Integer id);

    List<Tag> findAll();
}
