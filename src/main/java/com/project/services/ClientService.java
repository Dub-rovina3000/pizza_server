package com.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.AuthorizationClient;
import com.project.entity.Client;
import com.project.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public Client create(Client client) throws NoSuchAlgorithmException {
        if (clientRepo.findByEmailOrPhone(client.getEmail()) == null && clientRepo.findByEmailOrPhone(client.getPhoneNumber()) == null) {
            client.setPassword(getHashedPass(client.getPassword(), client.getSalt()));
            Client newClient = clientRepo.save(client);
            newClient.setPassword(null);
            newClient.setSalt(null);
            return newClient;
        }
        return null;
    }

    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    public Optional<Client> findById(Long id){
        return clientRepo.findById(id);
    }

    public boolean change(String jsonString) throws NoSuchAlgorithmException, JsonProcessingException {
        String newPassword = jsonString.substring(jsonString.indexOf("\"newPassword\":") + 15, jsonString.lastIndexOf("\"}"));
        jsonString = jsonString.substring(0, jsonString.indexOf(",\"newPassword\":")) + "}";
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(jsonString, Client.class);
        Client person = clientRepo.findByMyId(client.getId());
        if (person != null) {
            if (person.getPassword().equals(getHashedPass(client.getPassword(), person.getSalt()))) {
                Client client1 = clientRepo.findByEmailOrPhone(client.getEmail());
                Client client2 = clientRepo.findByEmailOrPhone(client.getPhoneNumber());
                if ((client1 == null || client1.equals(person)) && (client2 == null || client2.equals(person) )) {
                    person.setFirstName(client.getFirstName());
                    person.setEmail(client.getEmail());
                    person.setLastName(client.getLastName());
                    person.setPhoneNumber(client.getPhoneNumber());
                    if (!newPassword.equals("")) {
                        person.setPassword(getHashedPass(newPassword, person.getSalt()));
                    }
                    clientRepo.save(person);
                    return true;
                }
            }
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

    public Client findByEmailOrPhone(AuthorizationClient client) throws NoSuchAlgorithmException {
        Client fclient = clientRepo.findByEmailOrPhone(client.getLogin());

        if (fclient != null) {
            if (fclient.getPassword().equals(getHashedPass(client.getPassword(), fclient.getSalt()))) {
                fclient.setPassword(null);
                fclient.setSalt(null);
                return fclient;
            } else {
                return null;
            }
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
