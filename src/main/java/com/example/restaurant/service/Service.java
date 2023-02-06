package com.example.restaurant.service;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderStatus;
import com.example.restaurant.domain.Table;
import com.example.restaurant.repository.MenuRepository;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.utils.observer.Observer;
import com.example.restaurant.utils.utils.ChangeOrder;
import com.example.restaurant.validation.OrderValidator;
import com.example.restaurant.validation.ValidationException;

public class Service {

    private final TableRepository tables;
    private final MenuRepository menu;
    private final OrderRepository orders;
    private final OrderValidator orderValidator;

    public Service(TableRepository tables, MenuRepository menu, OrderRepository orders) {
        this.tables = tables;
        this.menu = menu;
        this.orders = orders;
        this.orderValidator = new OrderValidator();
    }

    public void updateOrderStatus(Order order, OrderStatus orderStatus){
        orders.updateStatus(order, orderStatus);
    }

    public Integer addOrder(Order order) throws ValidationException {
        orderValidator.validateOrder(order);
        return orders.add(order);
    }

    public Iterable<Order> findAllPlacedOrders(){
        return orders.findAllOrdersByStatus(OrderStatus.PLACED);
    }

    public Iterable<Order> findAllPreparingOrders(){
        return orders.findAllOrdersByStatus(OrderStatus.PREPARING);
    }

    public Iterable<Order> findAllDeliveredOrders(){
        return orders.findAllOrdersByStatus(OrderStatus.DELIVERED);
    }

    public Iterable<Table> findAllTables(){
        return tables.findAllTables();
    }

    public Iterable<String> findAllMenuCategories(){
        return menu.findAllCategories();
    }

    public Iterable<MenuItem> findAllMenuItemsInCategory(String category){
        return menu.findAllMenuItemsInCategory(category);
    }

    public void addObserver(Observer<ChangeOrder> observer){
        orders.addObserver(observer);
    }

}
