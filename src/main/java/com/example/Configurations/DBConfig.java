package com.example.Configurations;


import com.example.Job.CustomJobDao;
import com.example.Photo.PhotoDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean
    public CustomJobDao customJobDao() {return new CustomJobDao();}

    @Bean
    public PhotoDao photoDao() {return new PhotoDao();}

}
