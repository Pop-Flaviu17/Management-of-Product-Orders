package com.example.orders_management;

import com.example.orders_management.Connection.ConnectionFactory;
//import com.example.orders_management.Presentation.Main;
import com.example.orders_management.Model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Application extends javafx.application.Application {

    private static Stage stg;
    // Javadoc command line:
    // javadoc -d doc src\*
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 807, 545);
        stage.setTitle("Orders Management");
        stage.setScene(scene);
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    public void setSize(int newSize){
        stg.setWidth(newSize);
    }
    public static void main(String[] args) {
        launch();
    }
}