package com.navpahul.KTems.data;

public class LoginDetails {
    private String usernameOrEmail;
    private String password;

    @Override
    public String toString(){
        return "usernameOrEmail" + this.usernameOrEmail + ", pass: " + this.password;
    }

    public LoginDetails(){}
    
    public LoginDetails(String usernameOrEmail, String password){
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail(){
        return this.usernameOrEmail;
    }

    public String getPassword(){
        return this.password;
    }
}
