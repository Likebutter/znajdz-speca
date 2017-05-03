package com.example.model.Configurations;


import com.example.model.Company.CustomCompanyDao;
import com.example.model.Job.CustomJobDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {

    @Bean
    public CustomJobDao customJobDao() {return new CustomJobDao();}

    @Bean
    public CustomCompanyDao customComapnyDao() {return new CustomCompanyDao();}

}
