package com.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.entity.AuthorizationClient;
import com.project.entity.Employee;
import com.project.repositories.EmployeeRepo;
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
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepo employeeRepo;

    @Test
    public void create() throws NoSuchAlgorithmException {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setPhoneNumber("+7 999 999 99 99");

        Assert.assertTrue(employeeService.create(employee));
    }

    @Test
    public void findAll() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee());

        Mockito.doReturn(employeeList).when(employeeRepo).findAll();
        Assert.assertEquals(employeeList, employeeService.findAll());
    }

    @Test
    public void findById() {
        Employee employee = new Employee();
        Optional optionalEmployee = Optional.of(employee);
        Long id = 1L;

        Mockito.doReturn(optionalEmployee).when(employeeRepo).findById(id);
        Assert.assertEquals(optionalEmployee, employeeService.findById(id));
    }

    @Test
    public void change() throws JsonProcessingException, NoSuchAlgorithmException {
        Employee employee = new Employee();
        Long id = 1L;
        employee.setId(id);
        employee.setFirstName("John");
        employee.setPassword("11111");
        Employee employee1 = new Employee();
        employee1.setId(id);
        employee1.setFirstName("John");
        employee1.setPassword("5d313b068cb13d5f059a7b47da0a9ae1");
        employee1.setSalt("fp71ssz66");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonString = ow.writeValueAsString(employee) + ",\"newPassword\":\"123\"}";

        Mockito.doReturn(employee1).when(employeeRepo).findByMyId(id);
        Assert.assertTrue(employeeService.change(jsonString));
    }

    @Test
    public void delete() {
        Employee employee = new Employee();
        Long id = 1L;
        employee.setId(id);
        Optional optionalEmployee = Optional.of(employee);

        Mockito.doReturn(optionalEmployee).when(employeeRepo).findById(id);
        Assert.assertTrue(employeeService.delete(id));
    }

    @Test
    public void findByLogin() throws NoSuchAlgorithmException {
        AuthorizationClient employee1 = new AuthorizationClient();
        Employee employee = new Employee();
        Long id = 1L;
        employee1.setLogin("login");
        employee1.setPassword("11111");

        employee.setPassword("5d313b068cb13d5f059a7b47da0a9ae1");
        employee.setSalt("fp71ssz66");

        Mockito.doReturn(employee).when(employeeRepo).findByLogin("login");
        Assert.assertEquals(employee, employeeService.findByLogin(employee1));
    }
}