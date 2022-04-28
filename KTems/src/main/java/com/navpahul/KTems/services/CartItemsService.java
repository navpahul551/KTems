package com.navpahul.KTems.services;

import com.navpahul.KTems.entities.CartItemsDetails;
import com.navpahul.KTems.repositories.CartItemsRepository;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartItemsService {
    
    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository){
        this.cartItemsRepository = cartItemsRepository;
    }

    public void deleteCartItemById(Long cartItemId) throws NotFoundException{
        CartItemsDetails cartItemsdToDelete = cartItemsRepository.findById(cartItemId).orElseThrow(NotFoundException::new);

        cartItemsRepository.delete(cartItemsdToDelete);
    }
}
