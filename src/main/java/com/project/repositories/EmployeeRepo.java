package com.project.repositories;

import com.project.entity.Client;
import com.project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM employee WHERE login = :login")
    Employee findByLogin(@Param("login") String login);

    @Query(nativeQuery = true, value = "SELECT * FROM employee WHERE id = :id")
    Employee findByMyId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM employee WHERE email = :search OR phone_number = :search")
    Employee findByEmailOrPhone(@Param("search") String search);
}
