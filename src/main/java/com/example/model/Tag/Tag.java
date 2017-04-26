package com.example.model.Tag;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "tag_seq", initialValue = 1, allocationSize = 1)
    private int id;

    @NotNull
    private String name;

    public Tag(){}

    public Tag(String name) {
        this.name = name;
    }

}
