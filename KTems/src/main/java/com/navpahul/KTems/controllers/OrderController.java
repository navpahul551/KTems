package com.navpahul.KTems.controllers;

import com.navpahul.KTems.exceptions.OrderNotFoundException;
import com.navpahul.KTems.services.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {
    
    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // @PostMapping("place")
    // public ResponseEntity<?> addOrder(@RequestParam(name="cart_id") Long cartId){
    //     try{
    //         return ResponseEntity.ok(orderService.addOrder(cartId));
    //     }
    //     catch(CartNotFoundException e){
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    //     }
    //     catch(Exception e){
    //         return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @DeleteMapping("{id}/cancel")
    public ResponseEntity<?> deleteOrder(@PathVariable(name="id") Long orderId){
        try{
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order has been deleted successfully!!!");
        }
        catch(OrderNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrderDetails(@PathVariable(name="id") Long orderId){
        try{
            return ResponseEntity.ok(orderService.getOrderDetails(orderId));
        }
        catch(OrderNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
