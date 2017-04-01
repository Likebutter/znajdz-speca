package com.example.Localization;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Localization {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    public Localization() {
    }

    public Localization(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Localization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
