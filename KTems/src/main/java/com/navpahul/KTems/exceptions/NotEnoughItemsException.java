package com.navpahul.KTems.exceptions;

public class NotEnoughItemsException extends Exception{
    @Override
    public String getMessage(){
        return "Not enough items.";
    }
}
