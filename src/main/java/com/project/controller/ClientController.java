package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.project.entity.AuthorizationClient;
import com.project.entity.Client;
import com.project.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public ResponseEntity<?> create(@RequestBody Client client) throws NoSuchAlgorithmException {
        Client newClient = clientService.create(client);
        if (newClient != null) {
            return new ResponseEntity(newClient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/authorization/client")
    public ResponseEntity<?> authorize(@RequestBody AuthorizationClient client) throws NoSuchAlgorithmException {
        Client foundedClient = clientService.findByEmailOrPhone(client);

        if (foundedClient != null) {
            return new ResponseEntity<>(foundedClient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> findAll(){
        final List<Client> clientList = clientService.findAll();

        if (clientList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(clientList, HttpStatus.OK);
    }

    @PutMapping(value = "/clients")
    public ResponseEntity<?> change(@RequestBody String jsonString) throws NoSuchAlgorithmException, JsonProcessingException {
        if (clientService.change(jsonString)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (clientService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
