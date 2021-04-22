package com.project.controller;

import com.project.entity.Client;
import com.project.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public ResponseEntity<?> create(@RequestBody Client client){
        Client savedClient = clientService.create(client);
        if (savedClient == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> findAll(){
        final List<Client> clientList = clientService.findAll();

        if (clientList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(clientList, HttpStatus.OK);
    }

//    @GetMapping("/clients/{id}")
//    public ResponseEntity<Optional<Client>> findById(@PathVariable(name = "id") Long id ) {
//        final Optional<Client> client = clientService.findById(id);
//
//        if (client.isPresent()) {
//            return new ResponseEntity<>(client, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping(value = "/clients/{login}")
    public ResponseEntity<?> change(@RequestBody Client client, @PathVariable(name = "login") String search) {
        if (clientService.change(client, search)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (clientService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/clients/signIn")
    public ResponseEntity<Client> checkClient(@RequestBody Client client) {
        final Client clients = clientService.findByEmailOrPhone(client.getPhoneNumber());

        if (clients != null) {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
}
