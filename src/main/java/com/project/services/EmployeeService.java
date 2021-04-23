package com.project.services;

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

    public boolean change(Employee employee, Long id){
        if (findById(id).isPresent()) {
            employee.setId(id);
            employeeRepo.save(employee);
            return true;
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
