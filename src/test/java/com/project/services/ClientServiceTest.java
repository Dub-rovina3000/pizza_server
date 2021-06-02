package com.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.entity.AuthorizationClient;
import com.project.entity.Client;
import com.project.repositories.ClientRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepo clientRepo;

    @Test
    public void create() throws NoSuchAlgorithmException {
        Client client = new Client();
        client.setFirstName("John");
        client.setPhoneNumber("+7 999 999 99 99");

        Mockito.doReturn(client).when(clientRepo).save(client);
        Assert.assertEquals(client, clientService.create(client));
    }

    @Test
    public void findAll() {
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());

        Mockito.doReturn(clientList).when(clientRepo).findAll();
        Assert.assertEquals(clientList, clientService.findAll());
    }

    @Test
    public void findById() {
        Client client = new Client();
        Optional optionalClient = Optional.of(client);
        Long id = 1L;

        Mockito.doReturn(optionalClient).when(clientRepo).findById(id);
        Assert.assertEquals(optionalClient, clientService.findById(id));
    }

    @Test
    public void change() throws JsonProcessingException, NoSuchAlgorithmException {
        Client client = new Client();
        Long id = 1L;
        client.setId(id);
        client.setFirstName("John");
        client.setPassword("11111");
        Client client1 = new Client();
        client1.setId(id);
        client1.setFirstName("John");
        client1.setPassword("5d313b068cb13d5f059a7b47da0a9ae1");
        client1.setSalt("fp71ssz66");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = ow.writeValueAsString(client) + ",\"newPassword\":\"123\"}";

        Mockito.doReturn(client1).when(clientRepo).findByMyId(id);
        Assert.assertTrue(clientService.change(jsonString));
    }

    @Test
    public void delete() {
        Client client = new Client();
        Long id = 1L;
        client.setId(id);
        Optional optionalClient = Optional.of(client);

        Mockito.doReturn(optionalClient).when(clientRepo).findById(id);
        Assert.assertTrue(clientService.delete(id));
    }

    @Test
    public void findByEmailOrPhone() throws NoSuchAlgorithmException {
        AuthorizationClient client1 = new AuthorizationClient();
        Client client = new Client();
        Long id = 1L;
        client1.setLogin("login");
        client1.setPassword("11111");

        client.setPassword("5d313b068cb13d5f059a7b47da0a9ae1");
        client.setSalt("fp71ssz66");

        Mockito.doReturn(client).when(clientRepo).findByEmailOrPhone("login");
        Assert.assertEquals(client, clientService.findByEmailOrPhone(client1));
    }
}