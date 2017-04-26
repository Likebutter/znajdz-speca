package com.example.model.Client;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "public")
    @SequenceGenerator(name = "public", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
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

    @NotNull
    private Boolean enabled;

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
