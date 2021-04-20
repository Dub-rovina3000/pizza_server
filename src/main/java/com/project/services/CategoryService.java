package com.project.services;

import com.project.entity.Category;
import com.project.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public void create(Category category){
        categoryRepo.save(category);
    }

    public List<Category> findAll(){
        return categoryRepo.findAll();
    }

    public Optional<Category> findById(Long id){
        return categoryRepo.findById(id);
    }

    public boolean change(Category category, Long id){
        if (findById(id).isPresent()) {
            category.setId(id);
            categoryRepo.save(category);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        if (findById(id).isPresent()) {
            categoryRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
