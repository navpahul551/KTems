package com.navpahul.KTems.repositories;

import java.util.Optional;

import com.navpahul.KTems.entities.CartItemsDetails;

import org.springframework.data.repository.CrudRepository;

public interface CartItemsRepository extends CrudRepository<CartItemsDetails, Long> {
    public Optional<CartItemsDetails> findByCartIdAndItemId(Long cartId, Long itemId);
}
