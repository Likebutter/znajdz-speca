package com.pandathree.example.Client;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    public List<Client> findClientByEmail(String email);
}
