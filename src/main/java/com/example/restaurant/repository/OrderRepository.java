package com.example.restaurant.repository;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderStatus;
import com.example.restaurant.utils.observer.ConcreteObserver;
import com.example.restaurant.utils.utils.ChangeEventType;
import com.example.restaurant.utils.utils.ChangeOrder;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends ConcreteObserver<ChangeOrder> {

    private final String url;
    private final String username;
    private final String password;

    public OrderRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void updateStatus(Order order, OrderStatus newStatus){
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(url, username, password);

            String query = "UPDATE orders SET status = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newStatus.toString());
            statement.setInt(2, order.getId());

            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeConnection(connection);
        }

        order.setStatus(newStatus);

        this.notifyObservers(new ChangeOrder(ChangeEventType.UPDATE, order));
    }

    public Integer add(Order order){
        Connection connection = null;
        ResultSet generatedKey = null;
        Integer ID = null;

        try{

            connection = DriverManager.getConnection(url, username, password);

            String query = "INSERT INTO orders(\"table\", \"date\", status) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, order.getTable_id());
            statement.setTimestamp(2, Timestamp.valueOf(order.getDate()));
            statement.setString(3, order.getStatus().toString());

            statement.executeUpdate();
            generatedKey = statement.getGeneratedKeys();
            if(generatedKey.next()){
                ID = generatedKey.getInt(1);

                for(MenuItem menu_item : order.getItems()){

                    String insert_query = "INSERT INTO order_items(order_id, menu_item_id) " +
                            "VALUES (?, ?)";
                    PreparedStatement statement1 = connection.prepareStatement(insert_query);

                    statement1.setInt(1, ID);
                    statement1.setInt(2, menu_item.getId());

                    statement1.executeUpdate();
                }
            }


        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(generatedKey);
            DBUtils.closeConnection(connection);
        }

        this.notifyObservers(new ChangeOrder(ChangeEventType.ADD, order));
        return ID;
    }

    public Iterable<Order> findAllOrdersByStatus(OrderStatus orderStatus){
        Connection connection = null;
        ResultSet resultSet = null;
        ResultSet resultSet_items = null;
        List<Order> orders = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM orders WHERE status = ? ORDER BY date";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, orderStatus.toString());

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int ID = resultSet.getInt("id");
                Integer table_id = resultSet.getInt("table");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));

                List<MenuItem> items = new ArrayList<>();

                String query_items = "SELECT * FROM order_items WHERE order_id = ?";
                PreparedStatement statement_items = connection.prepareStatement(query_items);
                statement_items.setInt(1, ID);

                resultSet_items = statement_items.executeQuery();
                while(resultSet_items.next()){
                    Integer itemID = resultSet_items.getInt("menu_item_id");
                    MenuItem item = getMenuItem(itemID);
                    items.add(item);
                }

                orders.add(new Order(ID, table_id, items, date, status));

            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeResultSet(resultSet_items);
            DBUtils.closeConnection(connection);
        }

        return orders;
    }

    private MenuItem getMenuItem(Integer itemID) {
        Connection connection = null;
        ResultSet resultSet = null;
        MenuItem menuItem = null;

        try{

            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM menu WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, itemID);

            resultSet = statement.executeQuery();
            if(resultSet.next()){
                String category = resultSet.getString("category");
                String item = resultSet.getString("item");
                float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");

                menuItem = new MenuItem(itemID, category, item, price, currency);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConnection(connection);
        }

        return menuItem;
    }

}
