package com.navpahul.KTems.exceptions;

public class CartNotFoundException extends Exception {
    
    @Override
    public String getMessage(){
        return "Cart not found!!!";
    }
}
