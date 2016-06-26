package co.zero.vogue.model;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee extends BaseEntity{
    @ManyToOne
    private Area area;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;

    //TODO: Think about lomboz to delete this boilerplate code
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
