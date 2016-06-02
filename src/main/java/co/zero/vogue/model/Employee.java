package co.zero.vogue.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long firstName;
    private long lastName;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFirstName() {
        return firstName;
    }

    public void setFirstName(long firstName) {
        this.firstName = firstName;
    }

    public long getLastName() {
        return lastName;
    }

    public void setLastName(long lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
