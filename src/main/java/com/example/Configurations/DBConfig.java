package com.example.Configurations;


import com.example.Job.CustomJobDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean
    public CustomJobDao customJobDao() {return new CustomJobDao();}

}
