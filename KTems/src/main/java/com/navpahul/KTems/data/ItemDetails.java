package com.navpahul.KTems.data;

import com.navpahul.KTems.entities.Category;

public class ItemDetails {
    private Long id;
    private String name;
    private double price;
    private String description;
    private Long categoryId;
    private Long quantity;
    private Category category;

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public String getDescription(){
        return this.description;
    }

    public Long getCategoryId(){
        return this.categoryId;
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

    @Override
    public String toString(){
        return "price = " + this.price
                + "name = " + this.name
                + "description = " + this.description
                + "quantity = " + this.quantity;
    }
}
