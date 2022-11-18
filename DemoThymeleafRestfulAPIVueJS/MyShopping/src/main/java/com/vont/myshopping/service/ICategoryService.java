package com.vont.myshopping.service;

import com.vont.myshopping.models.entity.Account;
import com.vont.myshopping.models.entity.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Category save(Category category);
    void delete(long[] ids);
    Category findById(Long id);
//    List<Category> findByCategory(Long id);
    List<Category> findAll();
}
