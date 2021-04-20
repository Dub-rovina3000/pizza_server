package com.project.services;

import com.project.entity.Pizza;
import com.project.repositories.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepo pizzaRepo;

    public void create(Pizza pizza){
        pizzaRepo.save(pizza);
    }

    public List<Pizza> findAll(String sortBy){
        return pizzaRepo.findAll(Sort.by(
                Sort.Order.desc(sortBy)));
    }

    public Optional<Pizza> findById(Long id){
        return pizzaRepo.findById(id);
    }

    public boolean change(Pizza pizza, Long id){
        if (findById(id).isPresent()) {
            pizza.setId(id);
            pizzaRepo.save(pizza);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            pizzaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Pizza> findByCategory(String categories, String sortBy) {
        String[] categoryList = categories.split(",");
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc(sortBy)));
        List<Pizza> pizzas = pizzaRepo.findAllByOrderByNameAsc(categoryList, pageable);
        return pizzas;
    }
}
