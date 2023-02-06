module com.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.restaurant.controller to javafx.fxml;
    exports com.example.restaurant.controller;

    opens com.example.restaurant.utils.observer to javafx.fxml;
    exports com.example.restaurant.utils.observer;


    opens com.example.restaurant.utils.utils to javafx.fxml;
    exports com.example.restaurant.utils.utils;

    opens com.example.restaurant.domain to javafx.fxml;
    exports com.example.restaurant.domain;

    opens com.example.restaurant.service to javafx.fxml;
    exports com.example.restaurant.service;

    opens com.example.restaurant.repository to javafx.fxml;
    exports com.example.restaurant.repository;

    opens com.example.restaurant.validation to javafx.fxml;
    exports com.example.restaurant.validation;

    opens com.example.restaurant to javafx.fxml;
    exports com.example.restaurant;
}