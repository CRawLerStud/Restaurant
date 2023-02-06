package com.example.restaurant.controller;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderStatus;
import com.example.restaurant.service.Service;
import com.example.restaurant.utils.observer.Observer;
import com.example.restaurant.utils.utils.ChangeOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class StaffController implements Observer<ChangeOrder> {

    private Service service;

    @FXML
    TableView<Order> ordersTable;
    @FXML
    TableColumn<Order, Integer> tableColumn;
    @FXML
    TableColumn<Order, LocalDateTime> dataColumn;
    @FXML
    TableColumn<Order, List<MenuItem>> productsColumn;

    @FXML
    TableView<Order> preparingOrdersTable;
    @FXML
    TableColumn<Order, Integer> tablePreparingColumn;
    @FXML
    TableColumn<Order, LocalDateTime> dataPreparingColumn;
    @FXML
    TableColumn<Order, List<MenuItem>> productsPreparingColumn;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);

        initializeTables();
        updatePlacedOrdersTable();
        updatePreparingOrdersTable();
    }

    private void initializeTables(){
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        productsColumn.setCellValueFactory(new PropertyValueFactory<>("items"));

        tablePreparingColumn.setCellValueFactory(new PropertyValueFactory<>("table_id"));
        dataPreparingColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        productsPreparingColumn.setCellValueFactory(new PropertyValueFactory<>("items"));
    }

    private void updatePlacedOrdersTable(){
        List<Order> orders = (List<Order>) service.findAllPlacedOrders();
        ObservableList<Order> observableList = FXCollections.observableList(orders);
        ordersTable.setItems(observableList);
    }

    private void updatePreparingOrdersTable(){
        List<Order> orders = (List<Order>) service.findAllPreparingOrders();
        ObservableList<Order> observableList = FXCollections.observableList(orders);
        preparingOrdersTable.setItems(observableList);
    }

    @Override
    public void update(ChangeOrder event) {
        updatePlacedOrdersTable();
        updatePreparingOrdersTable();
    }

    @FXML
    public void prepareAction(){

        Order selectedPlacedOrder = ordersTable.getSelectionModel().getSelectedItem();
        if(selectedPlacedOrder == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No order selected!");
            alert.showAndWait();
            return;
        }

        service.updateOrderStatus(selectedPlacedOrder, OrderStatus.PREPARING);
    }

    @FXML
    public void deliverAction(){
        Order selectedPlacedOrder = preparingOrdersTable.getSelectionModel().getSelectedItem();
        if(selectedPlacedOrder == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "No order selected!");
            alert.showAndWait();
            return;
        }

        service.updateOrderStatus(selectedPlacedOrder, OrderStatus.DELIVERED);
    }
}
