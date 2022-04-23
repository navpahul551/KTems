package com.navpahul.KTems.exceptions;

public class ItemNotFoundException extends Exception {
    
    @Override
    public String getMessage(){
        return "Item not found";
    }
}
