package com.navpahul.KTems.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.NaturalId;

import lombok.Getter;

@Entity
@Table(name="roles")
@Getter
public class Role extends DateAudit {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NaturalId
    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private Rolename name;

    public Role() {}

    public Role(Rolename name){
        this.name = name;
    }

    public Rolename getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return this.name.toString();
    }
}
