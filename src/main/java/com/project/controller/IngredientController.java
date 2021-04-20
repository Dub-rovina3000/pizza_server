package com.project.controller;

import com.project.entity.Ingredient;
import com.project.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://pizzasite")
@RestController
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/ingredients")
    public void create(@RequestBody Ingredient ingredient){
        ingredientService.create(ingredient);
    }

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> findAll(){
        final List<Ingredient> ingredientList = ingredientService.findAll();

        if (ingredientList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(ingredientList, HttpStatus.OK);
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Optional<Ingredient>> findById(@PathVariable(name = "id") Long id ) {
        final Optional<Ingredient> ingredient = ingredientService.findById(id);

        if (ingredient.isPresent()) {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/ingredients/{id}")
    public ResponseEntity<?> change(@RequestBody Ingredient ingredient, @PathVariable(name = "id") Long id) {
        if (ingredientService.change(ingredient, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/ingredients/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (ingredientService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
