package com.example.restaurant.repository;

import com.example.restaurant.domain.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    private final String url;
    private final String username;
    private final String password;

    public MenuRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Iterable<MenuItem> findAllMenuItemsInCategory(String category){
        Connection connection = null;
        ResultSet resultSet = null;
        List<MenuItem> menu = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM menu WHERE \"category\" = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer ID = resultSet.getInt("id");
                String item = resultSet.getString("item");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");

                menu.add(new MenuItem(ID, category, item, price, currency));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConnection(connection);
        }

        return menu;
    }

    public Iterable<String> findAllCategories(){
        Connection connection = null;
        ResultSet resultSet = null;
        List<String> categories = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT category FROM menu GROUP BY category";
            PreparedStatement statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                String category = resultSet.getString("category");

                categories.add(category);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConnection(connection);
        }

        return categories;
    }

}
