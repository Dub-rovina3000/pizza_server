package com.project.controller;

import com.project.entity.Pizza;
import com.project.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://pizzasite")
@RestController
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @PostMapping("/pizzas")
    public void create(@RequestBody Pizza pizza){
        pizzaService.create(pizza);
    }

    @CrossOrigin(origins = "http://pizzasite")
    @GetMapping("/pizzas/{sortBy}")
    public ResponseEntity<List<Pizza>> findAll(@PathVariable(name = "sortBy") String sortBy){
        final List<Pizza> pizzaList = pizzaService.findAll(sortBy);

        if (pizzaList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(pizzaList, HttpStatus.OK);
    }

    @GetMapping("/pizzas/getById/{id}")
    public ResponseEntity<Optional<Pizza>> findById(@PathVariable(name = "id") Long id ) {
        final Optional<Pizza> pizza = pizzaService.findById(id);

        if (pizza.isPresent()) {
            return new ResponseEntity<>(pizza, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/pizzas/{id}")
    public ResponseEntity<?> change(@RequestBody Pizza pizza, @PathVariable(name = "id") Long id) {
        if (pizzaService.change(pizza, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/pizzas/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (pizzaService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://pizzasite")
    @GetMapping(value = "/pizzas/category/{categories}/{sortBy}")
    public ResponseEntity<List<Pizza>> findByCategory(@PathVariable(name = "categories") String categories, @PathVariable(name = "sortBy") String sortBy) {
        List<Pizza> pizzaSet = pizzaService.findByCategory(categories, sortBy);
        if (pizzaSet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pizzaSet, HttpStatus.OK);
    }
}
