package com.navpahul.KTems.exceptions;

public class UserNotFoundException extends Exception {
    
    @Override
    public String getMessage(){
        return "User not found!!!";
    }
}
