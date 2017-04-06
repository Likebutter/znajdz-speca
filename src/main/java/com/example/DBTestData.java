package com.example;


import com.example.Client.Client;
import com.example.Client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBTestData implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {

        Client client = new Client();
        client.setName("Jan");
        client.setLastname("Kowalski");
        client.setEmail("jankowalski@example.com");
        client.setPassword("12345678");
        client.setPhoneNumber("123456789");
        clientRepository.save(client);

    }
}
