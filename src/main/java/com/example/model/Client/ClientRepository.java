package com.example.model.Client;


import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    Client findById(Integer id);

    Client findByEmail(String email);
}
