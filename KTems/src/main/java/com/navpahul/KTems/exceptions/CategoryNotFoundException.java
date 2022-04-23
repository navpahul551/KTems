package com.navpahul.KTems.exceptions;

public class CategoryNotFoundException extends Exception {
    @Override 
    public String getMessage(){
        return "Category not found!!!";
    }
}
