package com.example.restaurant.domain;

public class Table extends Entity<Integer> {

    public Table(Integer ID) {
        this.setId(ID);
    }

    @Override
    public String toString() {
        return "Table " + this.getId();
    }
}
