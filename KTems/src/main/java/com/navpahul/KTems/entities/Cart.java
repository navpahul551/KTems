package com.navpahul.KTems.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="carts")
@Getter
public class Cart extends DateAudit{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public Cart(){}

    public Cart(User user){
        this.user = user;
    }

    public Long getId(){
        return this.id;
    }

    public User getUser(){
        return this.user;
    } 
}
