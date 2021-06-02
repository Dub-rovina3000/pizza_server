package com.project.services;

import com.project.entity.Category;
import com.project.entity.Ingredient;
import com.project.repositories.CategoryRepo;
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
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepo categoryRepo;

    @Test
    public void create() {
        Category category = new Category();
        category.setName("С сыром");

        Mockito.doReturn(category).when(categoryRepo).save(category);
        Assert.assertEquals(category, categoryService.create(category));
    }

    @Test
    public void findAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category());

        Mockito.doReturn(categories).when(categoryRepo).findAll();
        Assert.assertEquals(categories, categoryService.findAll());
    }

    @Test
    public void findById() {
        Category category = new Category();
        Optional expected = Optional.of(category);
        Long id = 1L;

        Mockito.doReturn(expected).when(categoryRepo).findById(id);
        Assert.assertEquals(expected, categoryService.findById(id));
    }

    @Test
    public void change() {
        Category category = new Category();
        Long id = 1L;
        category.setId(id);
        Optional expected = Optional.of(category);

        Mockito.doReturn(expected).when(categoryRepo).findById(id);
        Assert.assertTrue(categoryService.change(category, category.getId()));
    }

    @Test
    public void delete() {
        Category category = new Category();
        Long id = 1L;
        category.setId(id);
        Optional expected = Optional.of(category);

        Mockito.doReturn(expected).when(categoryRepo).findById(id);
        Assert.assertTrue(categoryService.delete(category.getId()));
    }
}