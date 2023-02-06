package com.example.restaurant;

import com.example.restaurant.controller.StaffController;
import com.example.restaurant.controller.TableController;
import com.example.restaurant.repository.MenuRepository;
import com.example.restaurant.repository.OrderRepository;
import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        String url = "jdbc:postgresql://localhost:5432/restaurant";
        String username = "postgres";
        String password = "postgres";

        TableRepository tableRepository = new TableRepository(url, username, password);
        MenuRepository menuRepository = new MenuRepository(url, username, password);
        OrderRepository orderRepository = new OrderRepository(url, username, password);

        Service service = new Service(tableRepository, menuRepository, orderRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("staff.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Staff");
        stage.setScene(scene);

        StaffController staffController = fxmlLoader.getController();
        staffController.setService(service);

        stage.show();

        service.findAllTables().forEach(table -> {
            FXMLLoader tableLoader = new FXMLLoader(HelloApplication.class.getResource("table.fxml"));
            Stage tableStage = new Stage();
            Scene tableScene;
            try {
                tableScene = new Scene(tableLoader.load(), 600, 400);
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
            tableStage.setScene(tableScene);
            tableStage.setTitle(table.toString());

            TableController controller = tableLoader.getController();
            controller.setService(service);
            controller.setTable(table);

            tableStage.show();
        });


    }

    public static void main(String[] args) {
        launch();
    }
}