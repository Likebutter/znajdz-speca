package com.example.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    public ClientController() {

    }


    @PostMapping(value = "/klient")
    public ResponseEntity<ClientResponse> registerNewClient(@RequestBody Client newClient) {

        Boolean correct = checkIfCorrectRequest(newClient);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(clientRepository.findByEmail(newClient.getEmail()) != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        clientRepository.save(newClient);
        ClientResponse response = new ClientResponse(newClient);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/klient/{id}")
    public ResponseEntity<ClientResponse> updateExistingClient(@PathVariable Integer id, @RequestBody Client updatedClient) {

        Boolean correct = checkIfCorrectRequest(updatedClient);

        if(!correct)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Client client = clientRepository.findById(id);

        if(client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Client checkedClient = clientRepository.findByEmail(updatedClient.getEmail());

        if(checkedClient != null)
            if(checkedClient.getId() != id)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        updatedClient.setId(id);
        clientRepository.save(updatedClient);
        ClientResponse response = new ClientResponse(updatedClient);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/klient/{id}")
    public ResponseEntity<ClientResponse> getClientData(@PathVariable Integer id) {

        Client client = clientRepository.findById(id);

        if(client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ClientResponse response = new ClientResponse(client);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Boolean checkIfCorrectRequest(Client client) {

        if(client.getEmail() == null)   return false;
        if(client.getName() == null)    return false;
        if(client.getLastname() == null)    return false;
        if(client.getPassword() == null)    return false;

        return true;
    }
}
