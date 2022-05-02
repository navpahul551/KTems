package com.navpahul.KTems.repositories;

import java.util.List;
import java.util.Optional;

import com.navpahul.KTems.entities.CartItems;

import org.springframework.data.repository.CrudRepository;

public interface CartItemsRepository extends CrudRepository<CartItems, Long> {
    public Optional<CartItems> findByCartIdAndItemId(Long cartId, Long itemId);
    public List<CartItems> findAllByCartId(Long cartId);
}
