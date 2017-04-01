package com.example.Client;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String imie;

    @NotNull
    private String nazwisko;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    public Client() {
    }

    public Client(String imie, String nazwisko, String email, String password, String phoneNumber) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Client(String imie, String nazwisko, String email, String password) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
