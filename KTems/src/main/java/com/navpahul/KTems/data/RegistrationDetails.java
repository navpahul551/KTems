package com.navpahul.KTems.data;

import java.util.List;

import com.navpahul.KTems.entities.Role;

public class RegistrationDetails {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private List<Role> roles;

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public List<Role> getRoles(){
        return this.roles;
    }

    @Override
    public String toString(){
        return "username: " + username
                + "password: " + password
                + "email: " + email
                + "firstName: " + firstName
                + "lastName: " + lastName;
    }
}
