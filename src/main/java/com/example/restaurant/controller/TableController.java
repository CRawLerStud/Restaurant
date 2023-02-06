package com.example.restaurant.controller;

import com.example.restaurant.HelloApplication;
import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderStatus;
import com.example.restaurant.domain.Table;
import com.example.restaurant.service.Service;
import com.example.restaurant.utils.observer.Observer;
import com.example.restaurant.utils.utils.ChangeOrder;
import com.example.restaurant.validation.ValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class TableController implements Observer<ChangeOrder> {

    private Service service;
    private Table table;
    private final Set<String> categories = new HashSet<>();
    private final Map<String, CategoryController> categoryControllerMap = new HashMap<>();

    @FXML
    Label tableLabel;

    @FXML
    ScrollPane categoryScrollPane;

    @FXML
    Button placeOrderButton;

    public void setService(Service service){
        this.service = service;
        service.addObserver(this);
    }

    public void setTable(Table table){
        this.table = table;
        tableLabel.setText(table.toString());

        initializeCategoriesBox();
    }

    private void initializeCategoriesBox(){

        VBox newVbox = new VBox();
        Iterable<String> categories = service.findAllMenuCategories();
        categories.forEach(category -> {

            FXMLLoader categoryLoader = new FXMLLoader(HelloApplication.class.getResource("categoryVBox.fxml"));
            VBox categoryVBox;
            try {
                categoryVBox = categoryLoader.load();
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }

            CategoryController controller = categoryLoader.getController();
            controller.setService(service);
            controller.setCategory(category);
            controller.setTable(table);

            categoryControllerMap.put(category, controller);

            newVbox.getChildren().add(categoryVBox);

            this.categories.add(category);

        });

        categoryScrollPane.setContent(newVbox);

    }

    public void placeOrderAction(){

        List<MenuItem> selectedItems = new ArrayList<>();

        for(String category : categories){
            List<MenuItem> selectedItemsCategory = categoryControllerMap.get(category).getSelectedItems();
            selectedItems.addAll(selectedItemsCategory);
        }

        Order newOrder = new Order(0, table.getId(), selectedItems, OrderStatus.PLACED);
        try {
            service.addOrder(newOrder);
        }
        catch(ValidationException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void update(ChangeOrder event) {
        if(event.getEventType().toString().equals("UPDATE") && event.getOrder().getTable_id().equals(table.getId())){

            if(event.getOrder().getStatus().toString().equals("PREPARING")) {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Your order is being prepared!");
                newAlert.setTitle("TABLE " + table.getId().toString());
                newAlert.showAndWait();
            }
            if(event.getOrder().getStatus().toString().equals("DELIVERED")){
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Your order is being delivered to you!");
                newAlert.setTitle("TABLE " + table.getId().toString());
                newAlert.showAndWait();
            }
        }
    }
}
