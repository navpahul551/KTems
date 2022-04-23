package com.navpahul.KTems.repositories;

import java.util.List;
import java.util.Optional;

import com.navpahul.KTems.entities.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public Optional<Category> findById(Long id);
    public List<Category> findAll();
}
