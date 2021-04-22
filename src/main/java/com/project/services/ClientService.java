package com.project.services;

import com.project.entity.Client;
import com.project.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public Client create(Client client){
        if (clientRepo.findByEmailOrPhone(client.getEmail()) == null && clientRepo.findByEmailOrPhone(client.getPhoneNumber()) == null) {
            try {
                client.setPassword(getHashedPass(client.getPassword(), client.getSalt()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            clientRepo.save(client);
            return clientRepo.findByEmailOrPhone(client.getEmail());
        } else {
            return null;
        }
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

    private String getHashedPass(String pass, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest((pass + salt).getBytes());

        BigInteger no = new BigInteger(1, messageDigest);

        StringBuilder encoded = new StringBuilder(no.toString(16));

        while (encoded.length() < 32) {
            encoded.insert(0, "0");
        }

        return encoded.toString();
    }
}
