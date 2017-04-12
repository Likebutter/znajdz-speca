package com.example.Company;


public class CompanyResponse {

    private Integer id;
    private String name;
    private String email;
    private String localization;
    private String phone;
    private String descript;
    private float avgRating;
    private int numberJobs;
    private int numberOpinions;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer id, String name, String email, String localization, String phone, String descript, float avgRating, int numberJobs, int numberOpinions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.localization = localization;
        this.phone = phone;
        this.descript = descript;
        this.avgRating = avgRating;
        this.numberJobs = numberJobs;
        this.numberOpinions = numberOpinions;
    }

    public CompanyResponse(Company company) {
        id = company.getId();
        name = company.getName();
        email = company.getEmail();
        localization = company.getLocalization();
        phone = company.getPhoneNumber();
        descript = company.getDescript();
        avgRating = company.getAvgRating();
        numberJobs = company.getNumberJobs();
        numberOpinions = company.getNumberOpinions();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getNumberJobs() {
        return numberJobs;
    }

    public void setNumberJobs(int numberJobs) {
        this.numberJobs = numberJobs;
    }

    public int getNumberOpinions() {
        return numberOpinions;
    }

    public void setNumberOpinions(int numberOpinions) {
        this.numberOpinions = numberOpinions;
    }

    @Override
    public String toString() {
        return "CompanyResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", localization='" + localization + '\'' +
                ", phone='" + phone + '\'' +
                ", descript='" + descript + '\'' +
                ", avgRating=" + avgRating +
                ", numberJobs=" + numberJobs +
                ", numberOpinions=" + numberOpinions +
                '}';
    }
}
