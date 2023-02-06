package com.example.restaurant.domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 1562341231231L;
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
