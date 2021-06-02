package com.project.controller;

import com.project.entity.Order;
import com.project.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://pizzasite")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public Long create(@RequestBody Order order){
        return orderService.create(order);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> findAll(){
        final List<Order> orderList = orderService.findAll();

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/client/{id}")
    public ResponseEntity<List<Order>> findByClientId(@PathVariable(name = "id") Long id) {
        final List<Order> orderList = orderService.findByClientId(id);

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/cook")
    public ResponseEntity<List<Order>> findAllWithoutCook() {
        final List<Order> orderList = orderService.findOrderWithoutCook();

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/courier")
    public ResponseEntity<List<Order>> findAllWithoutCourier() {
        final List<Order> orderList = orderService.findOrderWithoutCourier();

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/cook/{id}")
    public ResponseEntity<List<Order>> findOrderByCook(@PathVariable(name = "id") Long id) {
        final List<Order> orderList = orderService.findByCook(id);

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/courier/{id}")
    public ResponseEntity<List<Order>> findOrderByCourier(@PathVariable(name = "id") Long id) {
        final List<Order> orderList = orderService.findByCourier(id);

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/cook/work/{id}")
    public ResponseEntity<List<Order>> findWorkOrderByCook(@PathVariable(name = "id") Long id) {
        final List<Order> orderList = orderService.findWorkByCook(id);

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/courier/work/{id}")
    public ResponseEntity<List<Order>> findWorkOrderByCourier(@PathVariable(name = "id") Long id) {
        final List<Order> orderList = orderService.findWorkByCourier(id);

        if (orderList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Optional<Order>> findById(@PathVariable(name = "id") Long id ) {
        final Optional<Order> order = orderService.findById(id);

        if (order.isPresent()) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/orders/{id}")
    public ResponseEntity<?> change(@RequestBody Order order, @PathVariable(name = "id") Long id) {
        if (orderService.change(order, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/orders/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (orderService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
