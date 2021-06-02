package com.project.services;

import com.project.entity.Pizza;
import com.project.repositories.PizzaRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaServiceTest {


    @Autowired
    private PizzaService pizzaService;

    @MockBean
    private PizzaRepo pizzaRepo;

    @Test
    public void create() {
        Pizza pizza = new Pizza();
        pizza.setName("Сыр");

        Mockito.doReturn(pizza).when(pizzaRepo).save(pizza);
        Assert.assertEquals(pizza, pizzaService.create(pizza));
    }

    @Test
    public void findAll() {
        List<Pizza> pizzaList = new ArrayList<>();
        pizzaList.add(new Pizza());
        String sortBy = "name";

        Mockito.doReturn(pizzaList).when(pizzaRepo).findAll(Sort.by(Sort.Order.desc(sortBy)));
        Assert.assertEquals(pizzaList, pizzaService.findAll(sortBy));
    }

    @Test
    public void findById() {
        Pizza pizza = new Pizza();
        Optional optionalPizza = Optional.of(pizza);
        Long id = 1L;

        Mockito.doReturn(optionalPizza).when(pizzaRepo).findById(id);
        Assert.assertEquals(optionalPizza, pizzaService.findById(id));
    }

    @Test
    public void change() {
        Pizza pizza = new Pizza();
        Long id = 1L;
        pizza.setId(id);
        Optional optionalPizza = Optional.of(pizza);

        Mockito.doReturn(optionalPizza).when(pizzaRepo).findById(id);
        Assert.assertTrue(pizzaService.change(pizza, pizza.getId()));
    }

    @Test
    public void delete() {
        Pizza pizza = new Pizza();
        Long id = 1L;
        pizza.setId(id);
        Optional optionalPizza = Optional.of(pizza);

        Mockito.doReturn(optionalPizza).when(pizzaRepo).findById(id);
        Assert.assertTrue(pizzaService.delete(pizza.getId()));
    }

    @Test
    public void findByCategory() {
        List<Pizza> pizzaList = new ArrayList<>();
        pizzaList.add(new Pizza());
        String sortBy = "name";
        String categories = "Сырная, мясная";
        String[] categoryList = categories.split(",");
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.desc(sortBy)));

        Mockito.doReturn(pizzaList).when(pizzaRepo).findAllByOrderByNameAsc(categoryList, pageable);
        Assert.assertEquals(pizzaList, pizzaService.findByCategory(categories, sortBy));
    }
}