package com.navpahul.KTems.repositories;

import java.util.List;
import java.util.Optional;

import com.navpahul.KTems.entities.Item;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    public List<Item> findAll();
    public Optional<Item> findById(Long id);
    public List<Item> findAllByCategoryId(Long categoryId);
}
