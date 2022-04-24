package com.navpahul.KTems.controllers;

import com.navpahul.KTems.entities.CartItemsDetails;
import com.navpahul.KTems.exceptions.CartNotFoundException;
import com.navpahul.KTems.exceptions.ItemNotFoundException;
import com.navpahul.KTems.exceptions.NotEnoughItemsException;
import com.navpahul.KTems.services.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carts")
public class CartController {
    
    private CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCartDetails(@PathVariable(name="id") Long cartId){
        try{
            return ResponseEntity.ok(cartService.getCartDetails(cartId));
        }
        catch(CartNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("{cartId}/addItems")
    public ResponseEntity<?> addItems(@PathVariable(name="cartId") Long cartId, @RequestBody CartItemsDetails cartItemsDetails){
        try{
            cartService.addItems(cartItemsDetails);
            return ResponseEntity.ok("Items have been added to the cart successfully!!!");
        }
        catch(CartNotFoundException | ItemNotFoundException | NotEnoughItemsException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // needs to be fixed
    // find the item to be removed based on three things or the primary key
    // @DeleteMapping("{cartId}/items/{itemId}/remove")
    // public ResponseEntity<?> removeItems(@PathVariable(name="cartId") Long cartId, @PathVariable(name="itemId") Long itemId){
    //     try{
    //         cartService.removeItemsFromCart(cartId, itemId);
    //         return ResponseEntity.ok("Items have been removed from the cart successfully!!!");
    //     }
    //     catch(CartNotFoundException | ItemNotFoundException e){
    //         e.printStackTrace();
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }
    //     catch(Exception e){
    //         e.printStackTrace();
    //         return new ResponseEntity<>("An error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}