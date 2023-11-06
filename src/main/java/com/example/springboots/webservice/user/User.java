package com.example.springboots.webservice.user;


import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class User {
    private Integer id;
    @Size(min = 2)
    private String name;
    @Past
    private Date dob;

    public User(Integer id, String name, Date dob) {
        super();
        this.id = id;
        this.name = name;
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                '}';
    }
}
