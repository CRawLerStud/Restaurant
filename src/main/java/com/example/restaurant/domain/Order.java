package com.example.restaurant.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Integer> {

    private Integer table_id;
    private List<MenuItem> items;
    private LocalDateTime date;
    private OrderStatus status;

    public Order(Integer ID, Integer table_id, List<MenuItem> items, LocalDateTime date, OrderStatus status) {
        this.setId(ID);
        this.table_id = table_id;
        this.items = items;
        this.date = date;
        this.status = status;
    }

    public Order(Integer ID, Integer table_id, List<MenuItem> items, OrderStatus status) {
        this.setId(ID);
        this.table_id = table_id;
        this.items = items;
        this.status = status;
        this.date = LocalDateTime.now();
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
