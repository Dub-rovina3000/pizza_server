package com.project.repositories;

import com.project.entity.Pizza;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PizzaRepo extends JpaRepository<Pizza, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM pizzas WHERE id in (SELECT pizza_id FROM pizzas_category_set WHERE category_set_id in :category)")
    List<Pizza> findAllByOrderByNameAsc(@Param("category") String[] category, Pageable pageable);


}