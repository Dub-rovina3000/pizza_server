package com.project.services;

import com.project.entity.Employee;
import com.project.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public void create(Employee employee){
        employeeRepo.save(employee);
    }

    public List<Employee> findAll(){
        return employeeRepo.findAll();
    }

    public Optional<Employee> findById(Long id){
        return employeeRepo.findById(id);
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
}
