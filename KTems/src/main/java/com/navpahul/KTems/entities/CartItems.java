package com.navpahul.KTems.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="cart_items")
@Getter
public class CartItems {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="cart_id")
    private Long cartId;

    @Column(name="item_id")
    private Long itemId;

    @Column(name="quantity")
    private Long quantity;

    // @Column(name="buying_price")
    // private double buyingPrice;
    
    public CartItems(Long cartId, Long itemId, Long quantity){
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public CartItems() {}

    public Long getId() {
        return this.id;
    }

    public Long getItemId(){
        return this.itemId;
    }

    public Long getCartId(){
        return this.cartId;
    }

    public Long getQuantity(){
        return this.quantity;
    }

    public void setQuantity(Long quantity){
        this.quantity = quantity;
    }
}
