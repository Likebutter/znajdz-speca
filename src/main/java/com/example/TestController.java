package com.example;

import com.example.Client.Client;
import com.example.Client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(value = "/client/add")
    public ResponseEntity<Client> addNewClient(@RequestBody Client client) {


        if(client.getName() == null)
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        if(client.getLastname() == null)
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        if(client.getEmail() == null)
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        if(client.getPassword() == null)
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);

        clientRepository.save(client);
        return new ResponseEntity<Client>(client,HttpStatus.OK);
    }
}
