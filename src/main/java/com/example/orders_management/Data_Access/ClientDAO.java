package com.example.orders_management.Data_Access;

import com.example.orders_management.Connection.ConnectionFactory;
import com.example.orders_management.Model.Client;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class contains the Data Access Object
 * methods of class Client
 */
public class ClientDAO extends AbstractDAO<Client>{
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

}
