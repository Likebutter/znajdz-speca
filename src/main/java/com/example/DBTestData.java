package com.example;


import com.example.Client.Client;
import com.example.Client.ClientRepository;
import com.example.Company.Company;
import com.example.Tag.Tag;
import com.example.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBTestData implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {

        Client client = new Client();
        client.setName("Jan");
        client.setLastname("Kowalski");
        client.setEmail("jankowalski@example.com");
        client.setPassword("12345678");
        client.setPhoneNumber("123456789");
        clientRepository.save(client);

        Tag tag1 = new Tag();
        tag1.setName("abc");
        tagRepository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setName("def");
        tagRepository.save(tag2);
    }
}
