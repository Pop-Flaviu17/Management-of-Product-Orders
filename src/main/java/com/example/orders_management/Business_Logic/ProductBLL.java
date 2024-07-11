package com.example.orders_management.Business_Logic;

import com.example.orders_management.Data_Access.ProductDAO;
import javafx.scene.control.Alert;

public class ProductBLL {
    private ProductDAO productDAO;
    public ProductBLL(){
        productDAO = new ProductDAO();
    }
    /**
     * This method calls upon the delete method from class ProductDAO
     * and validates its result.
     * @param productId This represents the id of the Product object which was deleted
     * @return If the method was successful it will return 1
     * if not, it will return 0
     */
    public int delete (int productId){
        int result = productDAO.delete(productId);
        if(result == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Deletion failed");
            alert.showAndWait();
        }
        return result;
    }
}
