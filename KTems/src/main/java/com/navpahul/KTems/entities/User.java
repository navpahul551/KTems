package com.navpahul.KTems.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Entity
@Table(name="users")
@Getter
public class User extends DateAudit{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="username")
    private String username;

    @Column(name="password")
    @JsonIgnore
    private String password;

    @Column(name="email")
    @Email
    private String email;

    @ManyToMany
    @JoinTable(name="user_role",
               joinColumns=@JoinColumn(name="role_id"),
               inverseJoinColumns=@JoinColumn(name="user_id"))
    private List<Role> roles;

    @Column(name="enabled")
    private boolean enabled;

    public User(){}

    public User(String firstName, String lastName, String username, String email, String password, List<Role> roles){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = true;
        this.roles = roles;
    }

    public Long getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
    
    public String getUsername(){
        return this.username;
    }

    public List<Role> getRoles(){
        return this.roles;
    }

    public boolean getEnabled(){
        return this.enabled;
    }

    public void setRoles(List<Role> roles){
        this.roles = roles;
    }
}
