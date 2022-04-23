package com.navpahul.KTems.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="orders")
@Getter
public class Order extends DateAudit {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="order_item",
               joinColumns=@JoinColumn(name="order_id"),
               inverseJoinColumns=@JoinColumn(name="item_id"))
    private List<Item> items = new ArrayList<Item>();

    public Order() {}

    public Order(User user, List<Item> items) {
        this.user = user;
        this.items = items;
    }

    public List<Item> getItems(){
        return this.items;
    }
}
