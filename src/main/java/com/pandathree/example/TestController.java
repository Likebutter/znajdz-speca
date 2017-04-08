package com.pandathree.example;

/*@RestController
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

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Integer id) {

        Client client = clientRepository.findOne(id);

        if(client == null)
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }
}*/
