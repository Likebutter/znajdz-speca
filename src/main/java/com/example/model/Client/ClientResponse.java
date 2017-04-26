package com.example.model.Client;


public class ClientResponse {

    private Integer id;
    private String name;
    private String lastname;
    private String email;
    private String phoneNumber;

    public ClientResponse() {
    }

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.lastname = client.getLastname();
        this.email = client.getEmail();

        String phoneNumber = client.getPhoneNumber();
        if(phoneNumber != null)
            this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ClientResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
