package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.CategoryDao;
import com.bfs.logindemo.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {

        List<Category> categories = categoryDao.findAll();

        categories = categories.stream()
                .filter(category -> category != null && category.getName() != null)
                .collect(Collectors.toList());

        return categories;
    }

    public String getCategoryNameById(int categoryId) {
        Category category = categoryDao.findById(categoryId);
        if (category == null || category.getName() == null) {
            throw new IllegalArgumentException("Category not found for ID: " + categoryId);
        }
        return category.getName();
    }
}