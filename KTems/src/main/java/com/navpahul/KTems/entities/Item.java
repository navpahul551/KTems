package com.navpahul.KTems.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="items")
@Getter
public class Item extends DateAudit{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
    @Column(name="quantity")
    private Long quantity;

    public Item() {}

    public Item(String name, double price, String description, Long quantity, Category category){
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public String getDescription(){
        return this.description;
    }

    public Long getQuantity(){
        return this.quantity;
    }

    public Long getId(){
        return this.id;
    }

    public Category getCategory(){
        return this.category;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setQuantity(Long quantity){
        this.quantity = quantity;
    }

    public void setCategory(Category category){
        this.category = category;
    }
}
