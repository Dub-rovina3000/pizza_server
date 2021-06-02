package com.project.services;

import com.project.entity.Ingredient;
import com.project.repositories.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepo ingredientRepo;

    public Ingredient create(Ingredient ingredient){
        return ingredientRepo.save(ingredient);
    }

    public List<Ingredient> findAll(){
        return ingredientRepo.findAll();
    }

    public Optional<Ingredient> findById(Long id){
        return ingredientRepo.findById(id);
    }

    public boolean change(Ingredient ingredient, Long id){
        if (findById(id).isPresent()) {
            ingredient.setId(id);
            ingredientRepo.save(ingredient);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            ingredientRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
