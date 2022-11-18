package com.vont.myshopping.service.impl;

import com.vont.myshopping.models.entity.Category;
import com.vont.myshopping.repository.CategoryRepository;
import com.vont.myshopping.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public void delete(long[] ids) {

    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
