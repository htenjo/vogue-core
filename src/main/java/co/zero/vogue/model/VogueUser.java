package co.zero.vogue.model;

import co.zero.vogue.common.type.RoleType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by htenjo on 5/30/16.
 */
@Entity
public class VogueUser extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;
    @Column(nullable = false)
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private Employee employee;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
