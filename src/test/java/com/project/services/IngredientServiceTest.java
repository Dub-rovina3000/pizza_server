package com.project.services;

import com.project.entity.Ingredient;
import com.project.repositories.IngredientRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;

    @MockBean
    private IngredientRepo ingredientRepo;

    @Test
    public void create() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Сыр");

        Mockito.doReturn(ingredient).when(ingredientRepo).save(ingredient);
        Assert.assertEquals(ingredient, ingredientService.create(ingredient));
    }

    @Test
    public void findAll() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient());

        Mockito.doReturn(ingredientList).when(ingredientRepo).findAll();
        Assert.assertEquals(ingredientList, ingredientService.findAll());
    }

    @Test
    public void findById() {
        Ingredient ingredient = new Ingredient();
        Optional optionalIngredient = Optional.of(ingredient);
        Long id = 1L;

        Mockito.doReturn(optionalIngredient).when(ingredientRepo).findById(id);
        Assert.assertEquals(optionalIngredient, ingredientService.findById(id));
    }

    @Test
    public void change() {
        Ingredient ingredient = new Ingredient();
        Long id = 1L;
        ingredient.setId(id);
        Optional optionalIngredient = Optional.of(ingredient);

        Mockito.doReturn(optionalIngredient).when(ingredientRepo).findById(id);
        Assert.assertTrue(ingredientService.change(ingredient, ingredient.getId()));
    }

    @Test
    public void delete() {
        Ingredient ingredient = new Ingredient();
        Long id = 1L;
        ingredient.setId(id);
        Optional optionalIngredient = Optional.of(ingredient);

        Mockito.doReturn(optionalIngredient).when(ingredientRepo).findById(id);
        Assert.assertTrue(ingredientService.delete(ingredient.getId()));
    }
}