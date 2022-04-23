package com.navpahul.KTems.services;

import java.util.List;

import com.navpahul.KTems.data.CategoryDetails;
import com.navpahul.KTems.entities.Category;
import com.navpahul.KTems.exceptions.CategoryNotFoundException;
import com.navpahul.KTems.repositories.CategoryRepository;
import com.navpahul.KTems.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository){
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(CategoryDetails categoryDetails) {
        Category newCategory = new Category(categoryDetails.getName(), categoryDetails.getDescription());
        return categoryRepository.save(newCategory);        
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long categoryId) throws CategoryNotFoundException{
        return categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
    }
}
