package com.navpahul.KTems.data;

import java.util.List;

import com.navpahul.KTems.entities.CartItems;
import com.navpahul.KTems.entities.User;

import lombok.Getter;

@Getter
public class CartDetails {
    private Long id;
    private User user;
    private List<CartItemsDetails> cartItemsDetails;
    
    public CartDetails(){}

    public CartDetails(Long id, User user){
        this.id = id;
        this.user = user;
    }

    public void setCartItemsDetails(List<CartItemsDetails> cartItemsDetails) {
        this.cartItemsDetails = cartItemsDetails;
    }
}
