package com.navpahul.KTems.exceptions;

public class OrderNotFoundException extends Exception {
    
    @Override
    public String getMessage(){
        return "Order not found!!!";
    }    
}
