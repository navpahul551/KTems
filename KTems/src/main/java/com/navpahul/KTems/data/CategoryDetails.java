package com.navpahul.KTems.data;

import java.util.List;

import com.navpahul.KTems.entities.Item;

public class CategoryDetails {

    private Long id;
    private String name;
    private String description;
    private List<Item> items;

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public List<Item> getItems(){
        return this.items;
    }

    @Override
    public String toString(){
        return "name: " + this.name
                + ", description: " + this.description;
    }
}
