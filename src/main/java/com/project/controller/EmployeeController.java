package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.entity.AuthorizationClient;
import com.project.entity.Client;
import com.project.entity.Employee;
import com.project.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://pizzasite")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<?> create(@RequestBody Employee employee) throws NoSuchAlgorithmException {
        if (employeeService.create(employee)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("/authorization/emp")
    public ResponseEntity<?> authorize(@RequestBody AuthorizationClient emp) throws NoSuchAlgorithmException {
        Employee employee = employeeService.findByLogin(emp);

        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll(){
        final List<Employee> employeeList = employeeService.findAll();

        if (employeeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable(name = "id") Long id ) {
        final Optional<Employee> employee = employeeService.findById(id);

        if (employee.isPresent()) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/employees")
    public ResponseEntity<?> change(@RequestBody String jsonString) throws JsonProcessingException, NoSuchAlgorithmException {
        if (employeeService.change(jsonString)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (employeeService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
