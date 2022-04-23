package com.navpahul.KTems.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Entity
@Table(name="categories")
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Category extends DateAudit {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    public Category(){}
    
    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString(){
        return "name: " + this.name
                + ", description: " + this.description;
    }
}
