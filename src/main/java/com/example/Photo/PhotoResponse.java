package com.example.Photo;


public class PhotoResponse {

    private Integer id;
    private String url;

    public PhotoResponse() {
    }

    public PhotoResponse(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public PhotoResponse(Photo photo) {
        this.id = photo.getId();
        this.url = photo.getPhotoURL();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
