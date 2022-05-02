package com.navpahul.KTems.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.navpahul.KTems.data.CartDetails;
import com.navpahul.KTems.data.CartItemsDetails;
import com.navpahul.KTems.entities.Cart;
import com.navpahul.KTems.entities.CartItems;
import com.navpahul.KTems.entities.Item;
import com.navpahul.KTems.exceptions.CartNotFoundException;
import com.navpahul.KTems.exceptions.ItemNotFoundException;
import com.navpahul.KTems.exceptions.NotEnoughItemsException;
import com.navpahul.KTems.repositories.CartItemsRepository;
import com.navpahul.KTems.repositories.CartRepository;
import com.navpahul.KTems.repositories.ItemRepository;

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

    /**
     * Gets the cart details from the cart's id (PRIMARY KEY).
     * @param cartId
     * @return
     * @throws Exception
     */
    public CartDetails getCartDetails(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
        
        CartDetails cartDetails = new CartDetails(cart.getId(), cart.getUser());

        System.out.println("now finding the items details in the cart: ");
        List<CartItems> cartItems = cartItemsRepository.findAllByCartId(cartId);

        // find the details of all the items in the cart and 
        // add them in the cartDetails object
        List<CartItemsDetails> cartItemsDetailsList = new ArrayList<CartItemsDetails>();

        for(var item : cartItems){
            Optional<Item> optionalItem = itemRepository.findById(item.getItemId());
            
            if(optionalItem.isPresent()){
                CartItemsDetails cartItemsDetails = new CartItemsDetails(cart.getId(), item.getItemId(), item.getQuantity());
                cartItemsDetails.setId(item.getId());
                cartItemsDetails.setItemDetails(optionalItem.get()); 
                cartItemsDetailsList.add(cartItemsDetails);
            }
        }

        cartDetails.setCartItemsDetails(cartItemsDetailsList);

        return cartDetails;
    }

    /**
     * Adds the selected item to the cart if the quantity is available in the cart.
     * @param cartItemsDetailsToAdd
     * @throws CartNotFoundException
     * @throws ItemNotFoundException
     * @throws NotEnoughItemsException
     */
    public void addItems(CartItemsDetails cartItemsDetailsToAdd) throws CartNotFoundException, ItemNotFoundException, NotEnoughItemsException {
        cartRepository.findById(cartItemsDetailsToAdd.getCartId()).orElseThrow(CartNotFoundException::new);

        Item item = itemRepository.findById(cartItemsDetailsToAdd.getItemId()).orElseThrow(ItemNotFoundException::new);

        if(item.getQuantity() < cartItemsDetailsToAdd.getBoughtQuantity()){
            throw new NotEnoughItemsException();
        }

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByCartIdAndItemId(cartItemsDetailsToAdd.getCartId(), cartItemsDetailsToAdd.getItemId());
        
        CartItems cartItems = null;

        if(optionalCartItems.isPresent()){
            cartItems = optionalCartItems.get();
            if(item.getQuantity() >= cartItemsDetailsToAdd.getBoughtQuantity() + cartItems.getQuantity()){
                cartItems.setQuantity(cartItemsDetailsToAdd.getBoughtQuantity() + cartItems.getQuantity());
            }
            else{
                throw new NotEnoughItemsException();
            }
        }
        else{
            cartItems = new CartItems(cartItemsDetailsToAdd.getCartId(), cartItemsDetailsToAdd.getItemId(), cartItemsDetailsToAdd.getBoughtQuantity());
        }

        cartItemsRepository.save(cartItems);
    }
}
