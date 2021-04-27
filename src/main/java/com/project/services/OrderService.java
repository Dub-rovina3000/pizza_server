package com.project.services;

import com.project.entity.Employee;
import com.project.entity.Order;
import com.project.entity.Pizza;
import com.project.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public Long create(Order order){
        Set<Pizza> pizzaSet = new HashSet<>();
        String bufString = order.getPizzaAmount();
        while (bufString.contains("=")) {
            Long id = Long.valueOf(bufString.substring(0, bufString.indexOf("=")));
            Pizza pizza = new Pizza();
            pizza.setId(id);
            pizzaSet.add(pizza);
            bufString = bufString.substring(bufString.indexOf(";")+1);
        }
        order.setPizzaSet(pizzaSet);
        orderRepo.save(order);

        return order.getId();
    }

    public List<Order> findAll(){
        return orderRepo.findAll();
    }

    public List<Order> findByClientId(Long id) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findByClientId(id, pageable);
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

    public List<Order> findOrderWithoutCook() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findAllOrdersWithoutCook(pageable);
    }

    public List<Order> findOrderWithoutCourier() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findAllOrdersWithoutCourier(pageable);
    }

    public List<Order> findByCook(Long id) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findByCookId(id, pageable);
    }

    public List<Order> findByCourier(Long id) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findByCourier(id, pageable);
    }

    public List<Order> findWorkByCook(Long id) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findWorkByCookId(id, pageable);
    }

    public List<Order> findWorkByCourier(Long id) {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("order_time")));
        return orderRepo.findWorkByCourier(id, pageable);
    }
}
