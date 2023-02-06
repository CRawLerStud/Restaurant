package com.example.restaurant.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection(Connection connection){
        try{
            if(connection != null)
                connection.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
