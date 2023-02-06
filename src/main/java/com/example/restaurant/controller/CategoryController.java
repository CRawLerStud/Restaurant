package com.example.restaurant.controller;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Table;
import com.example.restaurant.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CategoryController {

    private Service service;
    private String category;
    private Table table;

    @FXML
    Label categoryLabel;

    @FXML
    TableView<MenuItem> categoryTable;

    @FXML
    TableColumn<MenuItem, String> nameColumn;

    @FXML
    TableColumn<MenuItem, Float> priceColumn;

    @FXML
    TableColumn<MenuItem, String> currencyColumn;

    public void setTable(Table table) {
        this.table = table;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setCategory(String category) {
        this.category = category;

        categoryLabel.setText(category);

        initializeTable();
        updateTable();
    }

    private void initializeTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        categoryTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateTable(){
        List<com.example.restaurant.domain.MenuItem> categoryItems = (List<com.example.restaurant.domain.MenuItem>) service.findAllMenuItemsInCategory(category);
        ObservableList<com.example.restaurant.domain.MenuItem> observableList = FXCollections.observableList(categoryItems);
        categoryTable.setItems(observableList);
    }

    public List<MenuItem> getSelectedItems(){
        return categoryTable.getSelectionModel().getSelectedItems();
    }

}
