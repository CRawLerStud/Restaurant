package com.example.restaurant.validation;

import com.example.restaurant.domain.Order;

public class OrderValidator {

    public void validateOrder(Order order) throws ValidationException{

        if(order.getItems().size() == 0)
            throw new ValidationException("The order is empty!");
    }

}
