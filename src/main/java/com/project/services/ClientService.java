package com.project.services;

import com.project.entity.Client;
import com.project.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public void create(Client client){
        clientRepo.save(client);
    }

    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    public Optional<Client> findById(Long id){
        return clientRepo.findById(id);
    }

    public boolean change(Client client, String search){
        Client person = findByEmailOrPhone(search);
        if (person != null) {
            client.setId(person.getId());
            clientRepo.save(client);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            clientRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Client findByEmailOrPhone(String search){
        return clientRepo.findByEmailOrPhone(search);
    }

    public Client checkPassword(Client enteredClient) {
        Client foundedClient;
        if (enteredClient.getEmail() != null) {
            foundedClient = findByEmailOrPhone(enteredClient.getEmail());
        } else {
            foundedClient = findByEmailOrPhone(enteredClient.getPhoneNumber());
        }
        if (foundedClient == null) {
            return null;
        }
        if (foundedClient.getPassword() == enteredClient.getPassword()) {
            return foundedClient;
        }
        return null;
    }
}
