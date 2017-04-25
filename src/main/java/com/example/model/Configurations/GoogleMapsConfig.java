package com.example.model.Configurations;

import com.google.maps.GeoApiContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsConfig {

    private String googleMapsApiKey;

    public GoogleMapsConfig() {
        this.googleMapsApiKey = "AIzaSyDUTHPAiZycAXa0it_h8cbdBjKmrJYJAdA";
    }

    @Bean
    public GeoApiContext GeoApiContext() {
        GeoApiContext context = new GeoApiContext();
        context.setApiKey(googleMapsApiKey);
        return context;
    }

}
