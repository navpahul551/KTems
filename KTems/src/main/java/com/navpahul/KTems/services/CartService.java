package com.navpahul.KTems.services;

import com.navpahul.KTems.entities.Cart;
import com.navpahul.KTems.entities.CartItemsDetails;
import com.navpahul.KTems.entities.Item;
import com.navpahul.KTems.exceptions.CartNotFoundException;
import com.navpahul.KTems.exceptions.ItemNotFoundException;
import com.navpahul.KTems.exceptions.NotEnoughItemsException;
import com.navpahul.KTems.repositories.CartItemsRepository;
import com.navpahul.KTems.repositories.CartRepository;
import com.navpahul.KTems.repositories.ItemRepository;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ItemRepository itemRepository;
    private CartItemsRepository cartItemsRepository;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository, CartItemsRepository cartItemsRepository){
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    public Cart getCartDetails(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        return cart;
    }

    public void addItems(CartItemsDetails cartItemsDetailsToAdd) throws CartNotFoundException, ItemNotFoundException, NotEnoughItemsException {
        cartRepository.findById(cartItemsDetailsToAdd.getCartId()).orElseThrow(CartNotFoundException::new);

        Item item = itemRepository.findById(cartItemsDetailsToAdd.getItemId()).orElseThrow(ItemNotFoundException::new);

        if(item.getQuantity() < cartItemsDetailsToAdd.getQuantity()){
            throw new NotEnoughItemsException();
        }

        CartItemsDetails cartItemsDetails = new CartItemsDetails(cartItemsDetailsToAdd.getCartId(), cartItemsDetailsToAdd.getItemId(), cartItemsDetailsToAdd.getQuantity());

        cartItemsRepository.save(cartItemsDetails);
    }
    
    public void removeItemsFromCart(Long cartId, Long itemId) throws CartNotFoundException, ItemNotFoundException, NotFoundException{
        CartItemsDetails cartItemsDetails = cartItemsRepository.findByCartIdAndItemId(cartId, itemId).orElseThrow(NotFoundException::new);

        cartItemsRepository.delete(cartItemsDetails);
    }
}
