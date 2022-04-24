package com.navpahul.KTems.repositories;

import java.util.List;

import com.navpahul.KTems.entities.CartItemsDetails;

import org.springframework.data.repository.CrudRepository;

public interface CartItemsRepository extends CrudRepository<CartItemsDetails, Long> {
    public List<CartItemsDetails> findByCartIdAndItemId(Long cartId, Long itemId);
    public List<CartItemsDetails> findAllByCartId(Long cartId);
}
