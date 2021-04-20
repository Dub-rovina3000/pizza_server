package com.project.services;

import com.project.entity.Ingredient;
import com.project.entity.Order;
import com.project.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public void create(Order order){
        orderRepo.save(order);
    }

    public List<Order> findAll(){
        return orderRepo.findAll();
    }

    public Optional<Order> findById(Long id){
        return orderRepo.findById(id);
    }

    public boolean change(Order order, Long id){
        if (findById(id).isPresent()) {
            order.setId(id);
            orderRepo.save(order);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            orderRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
