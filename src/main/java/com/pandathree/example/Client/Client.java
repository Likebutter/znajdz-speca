package com.pandathree.example.Client;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    public Client(){}

    public Client(String name, String lastname, String email, String password, String phoneNumber) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Client(String imie, String nazwisko, String email, String password) {
        this.name = imie;
        this.lastname = nazwisko;
        this.email = email;
        this.password = password;
    }

}
