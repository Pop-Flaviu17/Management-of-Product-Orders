package com.example.orders_management.Connection;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASS = "gardevoir17";
    private static final String DBNAME = "orders_management";
    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    /**
     * This method establishes a connection to the Database
     */
    private Connection connect_to_db(){
        Connection connection = null;
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DBURL + DBNAME, USER, PASS);
            if(connection != null){
                System.out.println("Connection established!");
            }else{
                System.out.println("Connection failed!");
            }
        }catch(Exception e){
            System.out.println("Flaviu has an ERROR in establishing connection!");
            System.out.println(e);
        }
        return connection;
    }

    /**
     * @return The connection object to the Database
     */
    public static Connection getConnection() {
        return singleInstance.connect_to_db();
    }
    /**
     * This method closes the opened connection to the Database
     * @param connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * This method closes the statement which was used to execute the query
     * @param statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * This method closes the resultSet which was used to store the
     * data from the table
     * @param resultSet
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
