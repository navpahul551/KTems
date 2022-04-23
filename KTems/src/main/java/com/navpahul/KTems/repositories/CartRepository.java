package com.navpahul.KTems.repositories;

import java.util.Optional;

import com.navpahul.KTems.entities.Cart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
    public Optional<Cart> findById(Long id);
    public Optional<Cart> findByUserId(Long userId);
}
