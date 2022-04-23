package com.navpahul.KTems.data;

public class ErrorResponse {
    private String errorMessage;

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}
