package com.navpahul.KTems.controllers;

import com.navpahul.KTems.services.CartItemsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cartItems")
public class CartItemsController {
    private CartItemsService cartItemsService;

    public CartItemsController(CartItemsService cartItemsService){
        this.cartItemsService = cartItemsService;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCartItemById(@PathVariable(name="id") Long cartItemId){
        try{
            cartItemsService.deleteCartItemById(cartItemId);
            return ResponseEntity.ok("The item has been deleted!");
        }
        catch(Exception e){
            System.out.println("error message: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
