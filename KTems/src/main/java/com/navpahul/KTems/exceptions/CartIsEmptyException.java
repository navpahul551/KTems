package com.navpahul.KTems.exceptions;

public class CartIsEmptyException extends Exception {
    
    @Override
    public String getMessage(){
        return "There are no items in the cart!!!";
    }
}
