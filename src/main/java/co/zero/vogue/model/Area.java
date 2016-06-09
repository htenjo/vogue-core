package co.zero.vogue.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Area extends BaseEntity{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
