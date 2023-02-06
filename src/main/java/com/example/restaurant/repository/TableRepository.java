package com.example.restaurant.repository;

import com.example.restaurant.domain.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableRepository {

    private final String url;
    private final String username;
    private final String password;

    public TableRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Iterable<Table> findAllTables(){
        Connection connection = null;
        ResultSet resultSet = null;
        List<Table> tables = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(url, username, password);

            String query = "SELECT * FROM tables";
            PreparedStatement statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            while(resultSet.next()){
                Integer ID = resultSet.getInt("id");
                tables.add(new Table(ID));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DBUtils.closeResultSet(resultSet);
            DBUtils.closeConnection(connection);
        }

        return tables;
    }

}
