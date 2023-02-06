package com.example.restaurant.utils.utils;

import com.example.restaurant.domain.Order;

public class ChangeOrder implements Event{

    private final ChangeEventType eventType;
    private final Order order;

    public ChangeOrder(ChangeEventType eventType, Order order) {
        this.eventType = eventType;
        this.order = order;
    }

    public ChangeEventType getEventType() {
        return eventType;
    }

    public Order getOrder() {
        return order;
    }
}
