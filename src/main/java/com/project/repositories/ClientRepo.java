package com.project.repositories;

import com.project.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepo extends JpaRepository<Client, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM client WHERE id = :id")
    Client findByMyId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM client WHERE email = :search OR phone_number = :search")
    Client findByEmailOrPhone(@Param("search") String search);


}
