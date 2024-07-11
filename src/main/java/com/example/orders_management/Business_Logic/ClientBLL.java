package com.example.orders_management.Business_Logic;

import com.example.orders_management.Data_Access.AbstractDAO;
import com.example.orders_management.Data_Access.ClientDAO;
import com.example.orders_management.Data_Access.ProductDAO;
import com.example.orders_management.Model.Client;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    /**
     * This field represents a list of the text validators for class Client
     */
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;
    //private AbstractDAO<Client> clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new PhoneValidator());
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id) {
        Client cl = clientDAO.findById(id);
        if (cl == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return cl;
    }

    /**
     * This method calls upon the delete method from class ClientDAO
     * and validates its result.
     * @param clientId This represents the id of the Client object which was deleted
     * @return If the method was successful it will return 1
     * if not, it will return 0
     */
    public int delete (int clientId){
        int result = clientDAO.delete(clientId);
        if(result == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Deletion failed");
            alert.showAndWait();
        }
        return result;
    }
}