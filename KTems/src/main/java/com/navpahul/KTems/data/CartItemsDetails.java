package com.navpahul.KTems.data;

import com.navpahul.KTems.entities.Item;

import lombok.Getter;

@Getter
public class CartItemsDetails {
    
    private Long id;

    private Long cartId;

    private Long itemId;

    private Long boughtQuantity;

    private Item itemDetails;
    
    public CartItemsDetails(Long cartId, Long itemId, Long boughtQuantity){
        this.cartId = cartId;
        this.itemId = itemId;
        this.boughtQuantity = boughtQuantity;
    }

    public CartItemsDetails() {}

    public Long getId() {
        return this.id;
    }

    public Long getItemId(){
        return this.itemId;
    }

    public Long getCartId(){
        return this.cartId;
    }

    public Long getBoughtQuantity(){
        return this.boughtQuantity;
    }

    public void setBoughtQuantity(Long boughtQuantity){
        this.boughtQuantity = boughtQuantity;
    }

    public void setItemDetails(Item itemDetails){
        this.itemDetails = itemDetails;
    }

    public void setId(Long id){
        this.id = id;
    }
}
