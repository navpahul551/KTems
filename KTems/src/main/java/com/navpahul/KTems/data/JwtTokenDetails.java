package com.navpahul.KTems.data;

public class JwtTokenDetails {
    private String token;
    private String type;

    public JwtTokenDetails(String token, String type){
        this.token = token;
        this.type = type;
    }

    public String getToken(){
        return this.token;
    }

    public String getType(){
        return this.type;
    }
}
