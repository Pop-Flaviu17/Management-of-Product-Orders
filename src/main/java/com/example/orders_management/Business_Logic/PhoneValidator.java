package com.example.orders_management.Business_Logic;

import com.example.orders_management.Model.Client;
import javafx.scene.control.Alert;

import java.util.regex.Pattern;

public class PhoneValidator implements Validator<Client>{
    /**
     * This method validates the field phone of class Client
     * @param t Represents the object Client which is to be validated
     */
    public void validate(Client t) {
        if (t.getPhone().length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Phone not valid!");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone is not a valid phone number!");
        }
    }
}
