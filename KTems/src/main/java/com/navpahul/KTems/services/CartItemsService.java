package com.navpahul.KTems.services;

import com.navpahul.KTems.entities.CartItems;
import com.navpahul.KTems.repositories.CartItemsRepository;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartItemsService {
    
    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository){
        this.cartItemsRepository = cartItemsRepository;
    }

    public void updateCartItemQuantityById(Long cartItemId, Long quantity) throws NotFoundException{
        CartItems cartItemsToUpdate = cartItemsRepository.findById(cartItemId).orElseThrow(NotFoundException::new);
        
        cartItemsToUpdate.setQuantity(quantity);

        cartItemsRepository.save(cartItemsToUpdate);
    }

    public void deleteCartItemById(Long cartItemId) throws NotFoundException{
        CartItems cartItemsdToDelete = cartItemsRepository.findById(cartItemId).orElseThrow(NotFoundException::new);

        cartItemsRepository.delete(cartItemsdToDelete);
    }
}
