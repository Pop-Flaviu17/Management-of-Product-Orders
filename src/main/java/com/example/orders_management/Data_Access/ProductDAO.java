package com.example.orders_management.Data_Access;

import com.example.orders_management.Connection.ConnectionFactory;
import com.example.orders_management.Model.Client;
import com.example.orders_management.Model.Product;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains the Data Access Object
 * methods of class Product
 */
public class ProductDAO extends AbstractDAO<Product>{
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private final static String checkStockStatementString = "SELECT stock FROM product WHERE idproduct = ?";
    private final static String updateStockStatementString = "UPDATE product SET stock = ? WHERE idproduct = ?";


    /**
     * This method get the stock field of an object Product
     * @param productId Represents the id of the object which
     *                  is to be checked
     * @return It returns the value of the stock field
     */
    public static int checkStock(int productId) {
        Client toReturn = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        int stock = -1;

        try {
            findStatement = dbConnection.prepareStatement(checkStockStatementString);
            findStatement.setLong(1, productId);
            rs = findStatement.executeQuery();
            rs.next();

            stock = rs.getInt("stock");
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:checkStock " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return stock;
    }

    /**
     * This method updates the Database with the new information
     * about the stock field of object Product
     * @param idProduct Represents the id of the object which is to be
     *                  updated
     * @param newStock Represents the new value of the stock field
     * @return It returns 1 if successful,
     * 0 otherwise
     */
    public static int updateStock (int idProduct, int newStock) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int update = 0;

        try {
            updateStatement = dbConnection.prepareStatement(updateStockStatementString);
            updateStatement.setInt(1, newStock);
            updateStatement.setInt(2, idProduct);
            updateStatement.executeUpdate();

            update = 1;
        } catch (SQLException e) {
            update = 0;
            LOGGER.log(Level.WARNING, "ProductDAO:updateStock " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return update;
    }
}
