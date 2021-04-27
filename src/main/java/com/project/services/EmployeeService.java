package com.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.AuthorizationClient;
import com.project.entity.Client;
import com.project.entity.Employee;
import com.project.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public boolean create(Employee employee) throws NoSuchAlgorithmException {
        if (employeeRepo.findByLogin(employee.getLogin()) == null) {
            employee.setPassword(getHashedPass(employee.getPassword(), employee.getSalt()));
            employeeRepo.save(employee);
            return true;
        }
        return false;
    }

    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Optional<Employee> findById(Long id){
        return employeeRepo.findById(id);
    }

    public Employee findByLogin(AuthorizationClient emp) throws NoSuchAlgorithmException {
        Employee employee = employeeRepo.findByLogin(emp.getLogin());

        if (employee != null) {
            if (employee.getPassword().equals(getHashedPass(emp.getPassword(), employee.getSalt()))) {
                employee.setPassword(null);
                employee.setSalt(null);
                return employee;
            } else {
                return null;
            }
        }
        return null;
    }

    public boolean change(String jsonString) throws NoSuchAlgorithmException, JsonProcessingException {
        String newPassword = jsonString.substring(jsonString.indexOf("\"newPassword\":") + 15, jsonString.lastIndexOf("\"}"));
        jsonString = jsonString.substring(0, jsonString.indexOf(",\"newPassword\":")) + "}";
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(jsonString, Employee.class);
        Employee person = employeeRepo.findByMyId(employee.getId());
        if (person != null) {
            if (person.getPassword().equals(getHashedPass(employee.getPassword(), person.getSalt()))) {
                Employee employee1 = employeeRepo.findByEmailOrPhone(employee.getEmail());
                Employee employee2 = employeeRepo.findByEmailOrPhone(employee.getPhoneNumber());
                Employee employee3 = employeeRepo.findByLogin(employee.getLogin());
                if ((employee1 == null || employee1.equals(person)) && (employee2 == null || employee2.equals(person) ) && (employee3 == null || employee3.equals(person) )) {
                    person.setFirstName(employee.getFirstName());
                    person.setEmail(employee.getEmail());
                    person.setLastName(employee.getLastName());
                    person.setPhoneNumber(employee.getPhoneNumber());
                    if (!newPassword.equals("")) {
                        person.setPassword(getHashedPass(newPassword, person.getSalt()));
                    }
                    employeeRepo.save(person);
                    return true;
                }
            }
        }
        return false;
    }


    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            employeeRepo.deleteById(id);
            return true;
        }
        return false;
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
