package com.example.orders_management.Data_Access;

import com.example.orders_management.Connection.ConnectionFactory;
import com.example.orders_management.Model.Client;
import com.example.orders_management.Model.Order;
import com.example.orders_management.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class contains the Data Access Object
 * methods of class Order
 */

public class OrderDAO extends AbstractDAO<Order>{
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    public void createTable(List<Client> list, TableView<Client> tbv){
        ObservableList<Client> obsList = FXCollections.observableArrayList();
        obsList.addAll(list);

        tbv.getColumns().get(0).setEditable(true);
        tbv.getColumns().get(0).setText("idClient");
        tbv.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("idClient"));

        tbv.getColumns().get(1).setEditable(true);
        tbv.getColumns().get(1).setText("Name");
        tbv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Name"));

        tbv.setItems(obsList);
        tbv.refresh();
    }

}
