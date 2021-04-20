package com.project.repositories;

import com.project.entity.Pizza;

import java.util.List;
import java.util.Set;

public interface PizzaRepoCustom {
    List<Pizza> findPizzaByCategorySet(Set<String> categorySet);
}
