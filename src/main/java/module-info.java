module com.example.pt2024_30422_pop_catalin_gabriel_flaviu_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;


    opens com.example.orders_management to javafx.fxml;
    exports com.example.orders_management;
    exports com.example.orders_management.Presentation;
    opens com.example.orders_management.Presentation to javafx.fxml;
    opens com.example.orders_management.Model to javafx.fxml, javafx.base;
}