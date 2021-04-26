package com.project.repositories;

import com.project.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE client_id = :id")
    List<Order> findByClientId(@Param("id") Long id, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE status = 4")
    List<Order> findAllOrdersWithoutCook(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE cook_id = :id and status < 3")
    List<Order> findByCookId(@Param("id") Long id, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE status = 3 and courier = null")
    List<Order> findAllOrdersWithoutCourier(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE courier_id = :id and status < 2")
    List<Order> findByCourier(@Param("id") Long id, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE courier_id = :id and status = 2")
    List<Order> findWorkByCourier(@Param("id") Long id, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM orders WHERE cook_id = :id and status = 3")
    List<Order> findWorkByCookId(@Param("id") Long id, Pageable pageable);
}
