package com.pandathree.example.Tag;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Tag {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    public Tag(){}

    public Tag(String name) {
        this.name = name;
    }

}
