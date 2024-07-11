package com.example.orders_management.Presentation;

import com.example.orders_management.Application;
import com.example.orders_management.Business_Logic.ClientBLL;
import com.example.orders_management.Business_Logic.ProductBLL;
import com.example.orders_management.Connection.ConnectionFactory;
import com.example.orders_management.Data_Access.AbstractDAO;
import com.example.orders_management.Data_Access.ClientDAO;
import com.example.orders_management.Data_Access.OrderDAO;
import com.example.orders_management.Data_Access.ProductDAO;
import com.example.orders_management.Model.Bill;
import com.example.orders_management.Model.Client;
import com.example.orders_management.Model.Order;
import com.example.orders_management.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller{
    private Stage stage;
    private Scene scene;
    private Parent root;

    // Main.fxml
    @FXML
    private Button clientOperations_btn;
    @FXML
    private Button orderOperations_btn;
    @FXML
    private Button productOperations_btn;
    // Client.fxml
    @FXML
    private Button addClient_btn;
    @FXML
    private Button viewClients_btn;
    @FXML
    private Button clientBack_btn;
    @FXML
    private Button deleteClient_btn;
    @FXML
    private Button editClient_btn;
    @FXML
    private Label client_lbl;
    @FXML
    private Label clientMessage_lbl;

    @FXML
    private TableView<Client> clientsTable;
    @FXML
    private TableColumn<Client, String> addressClientColum;
    @FXML
    private TableColumn<Client, String> emailClientColum;
    @FXML
    private TableColumn<Client, String> idClientColum;
    @FXML
    private TableColumn<Client, String> nameClientColum;
    @FXML
    private TableColumn<Client, String> phoneClientColum;

    // Product.fxml
    @FXML
    private Button addProduct_btn;
    @FXML
    private Button deleteProduct_btn;
    @FXML
    private Button editProduct_btn;
    @FXML
    private Button productBack_btn;
    @FXML
    private Button viewProducts_btn;
    @FXML
    private Label product_lbl;
    @FXML
    private Label productMessage_lbl;

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> idProductColum;
    @FXML
    private TableColumn<Product, String> nameProductColum;
    @FXML
    private TableColumn<Product, Double> priceProductColum;
    @FXML
    private TableColumn<Product, Integer> stockProductColum;

    // Order.fxml
    @FXML
    private Button viewBill_btn;
    @FXML
    private Button orderBack_btn;
    @FXML
    private Button orderData_btn;
    @FXML
    private Button order_btn;

    @FXML
    private Label successOrder_lbl;
    @FXML
    private TextField quantity_txt;

    @FXML
    private TableView<Client> clientOrderTable;
    @FXML
    private TableColumn<Client, Integer> idClientOrderColum;
    @FXML
    private TableColumn<Client, String> nameClientOrderColum;

    @FXML
    private TableView<Product> productOrderTable;
    @FXML
    private TableColumn<Product, Integer> idProductOrderColum;
    @FXML
    private TableColumn<Product, String> nameProductOrderColum;
    @FXML
    private TableColumn<Product, Double> priceProductOrderColum;
    @FXML
    private TableColumn<Product, Integer> stockProductOrderColum;

    // AddClient.fxml
    @FXML
    private TextField addressClientIAdd_txt;
    @FXML
    private TextField clientNameAdd_txt;
    @FXML
    private TextField emailClientIAdd_txt;
    @FXML
    private TextField idClientAdd_txt;
    @FXML
    private TextField phoneClientAdd_txt;

    @FXML
    private Label insertClientMessage_lbl;
    @FXML
    private Button insertClient_btn;

    // EditClient.fxml
    @FXML
    private TextField addressClientIEdit_txt = new TextField();
    @FXML
    private TextField clientNameEdit_txt = new TextField();
    @FXML
    private TextField emailClientIEdit_txt = new TextField();
    @FXML
    private TextField idClientEdit_txt = new TextField();
    @FXML
    private TextField phoneClientEdit_txt = new TextField();

    @FXML
    private Label editClientMessage_lbl;
    @FXML
    private Label idClientEdit_lbl;
    @FXML
    private Label nameClientEdit_lbl;
    @FXML
    private Label emailClientEdit_lbl;
    @FXML
    private Label phoneClientEdit_lbl;
    @FXML
    private Label addressClientEdit_lbl;
    @FXML
    private Button modifyClient_btn;

    // AddProduct.fxml
    @FXML
    private TextField priceProductAdd_txt;
    @FXML
    private TextField productNameAdd_txt;
    @FXML
    private TextField stockProductAdd_txt;
    @FXML
    private TextField idProductAdd_txt;

    @FXML
    private Button insertProduct_btn;
    @FXML
    private Label addProductMessage_lbl;

    // EditProduct.fxml
    @FXML
    private TextField idProductEdit_txt;
    @FXML
    private TextField priceProductEdit_txt;
    @FXML
    private TextField productNameEdit_txt;
    @FXML
    private TextField stockProductEdit_txt;

    @FXML
    private Button modifyProduct_btn;
    @FXML
    private Label editProductMessage_lbl;
    public Controller(){

    }

    // Instances for DAO managing
    ClientDAO daoClient = new ClientDAO();
    ProductDAO daoProduct = new ProductDAO();
    OrderDAO daoOrder = new OrderDAO();

    @FXML
    protected void onHelloButtonClick() {

    }
    @FXML
    public void onViewClientsButton(ActionEvent event) throws IOException {
        List<Client> list = daoClient.findAll();
        daoClient.generateTable(list, clientsTable);
        clientsTable.refresh();
    }
    @FXML
    public void onViewProductsButton(ActionEvent event) throws IOException {
        List<Product> list = daoProduct.findAll();
        /*
        idProductColum.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        nameProductColum.setCellValueFactory(new PropertyValueFactory<>("name"));;
        priceProductColum.setCellValueFactory(new PropertyValueFactory<>("price"));;
        stockProductColum.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        for(Product element:list) {
            productsTable.getItems().add(element);
        }
        */
        daoProduct.generateTable(list, productsTable);
        productsTable.refresh();
    }
    @FXML
    public void onViewClientsAndProductsButton(ActionEvent event) throws IOException {
        List<Client> clients = daoClient.findAll();
        List<Product> products = daoProduct.findAll();
        daoOrder.createTable(clients, clientOrderTable);
        daoProduct.generateTable(products, productOrderTable);
        clientOrderTable.refresh();
        productOrderTable.refresh();
    }
    @FXML
    public void onProductOperationsButton(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("../../../../../resources/com/example/orders_management/Product.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        */
        Application ap = new Application();
        ap.changeScene("Product.fxml");
        ap.setSize(815);
    }
    @FXML
    public void onGoBackButton(ActionEvent event) throws IOException {
        Application ap = new Application();
        ap.changeScene("Main.fxml");
        ap.setSize(815);
    }
    @FXML
    public void onClientOperationsButton(ActionEvent event) throws IOException {
        Application ap = new Application();
        ap.changeScene("Client.fxml");
        ap.setSize(815);
    }
    @FXML
    public void onOrderButton(ActionEvent event) throws IOException {
        Application ap = new Application();
        ap.changeScene("Order.fxml");
        ap.setSize(1000);
    }
    @FXML
    public void onAddProductButton(ActionEvent event) throws IOException {
        Application ap = new Application();
        ap.changeScene("AddProduct.fxml");
        ap.setSize(600);
    }
    @FXML
    public void onEditProductButton(ActionEvent event) throws IOException {
        Product product = productsTable.getSelectionModel().getSelectedItem();

        Application ap = new Application();
        ap.changeScene("EditProduct.fxml");
        ap.setSize(815);

        idProductEdit_txt.setText(String.valueOf(product.getIdProduct()));
        productNameEdit_txt.setText(product.getName());
        stockProductEdit_txt.setText(String.valueOf(product.getStock()));
        priceProductEdit_txt.setText(String.valueOf(product.getPrice()));

        ProductBLL result = new ProductBLL();
        result.delete(product.getIdProduct());
    }
    @FXML
    public void onAddClientButton(ActionEvent event) throws IOException {
        Application ap = new Application();
        ap.changeScene("AddClient.fxml");
        ap.setSize(600);
    }
    @FXML
    public void onEditClientButton(ActionEvent event) throws IOException {
        Client client = clientsTable.getSelectionModel().getSelectedItem();

        Application ap = new Application();
        ap.changeScene("EditClient.fxml");
        ap.setSize(815);

        idClientEdit_txt.setText(String.valueOf(client.getIdClient()));
        clientNameEdit_txt.setText(client.getName());
        emailClientIEdit_txt.setText(client.getEmail());
        phoneClientEdit_txt.setText(client.getPhone());
        addressClientIEdit_txt.setText(client.getAddress());

        idClientEdit_lbl.setText("ID: " + String.valueOf(client.getIdClient()));
        nameClientEdit_lbl.setText("Name: " + client.getName());
        emailClientEdit_lbl.setText("e-mail: " + client.getEmail());
        phoneClientEdit_lbl.setText("Phone: " + client.getPhone());
        addressClientEdit_lbl.setText("Address: " + client.getAddress());

        ClientBLL result = new ClientBLL();
        result.delete(client.getIdClient());
    }
    @FXML
    public void onDeleteProductButton(ActionEvent event) throws IOException {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        ProductBLL result = new ProductBLL();
        result.delete(product.getIdProduct());
        productMessage_lbl.setText("Product deleted!");
    }
    @FXML
    public void onDeleteClientButton(ActionEvent event) throws IOException {
        Client client = clientsTable.getSelectionModel().getSelectedItem();
        ClientBLL result = new ClientBLL();
        result.delete(client.getIdClient());
        clientMessage_lbl.setText("Client deleted!");
    }
    @FXML
    public void onInsertClientButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idClientAdd_txt.getText());
        String name = clientNameAdd_txt.getText();
        String email = emailClientIAdd_txt.getText();
        String phone = phoneClientAdd_txt.getText();
        String address = addressClientIAdd_txt.getText();

        Client client = new Client(id, name, email, phone, address);
        if (daoClient.insert(client) == -1){
            insertClientMessage_lbl.setText("Operation failed!");
        }else{
            insertClientMessage_lbl.setText("Client added!");
        }
    }
    @FXML
    public void onInsertProductButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idProductAdd_txt.getText());
        String name = productNameAdd_txt.getText();
        int stock = Integer.parseInt(stockProductAdd_txt.getText());
        Double price = Double.valueOf(priceProductAdd_txt.getText());

        Product product = new Product(id, name, stock, price);
        if (daoProduct.insert(product) == -1){
            addProductMessage_lbl.setText("Operation failed!");
        }else{
            addProductMessage_lbl.setText("Product added!");
        }
    }
    @FXML
    public void onModifyClientButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idClientEdit_txt.getText());
        String name = clientNameEdit_txt.getText();
        String email = emailClientIEdit_txt.getText();
        String phone = phoneClientEdit_txt.getText();
        String address = addressClientIEdit_txt.getText();

        Client client = new Client(id, name, email, phone, address);
        if (daoClient.update(client, "idClient", id) == 0){
            editClientMessage_lbl.setText("Operation failed!");
        }else{
            editClientMessage_lbl.setText("Client modified!");
        }
    }
    @FXML
    public void onModifyProductButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idProductEdit_txt.getText());
        String name = productNameEdit_txt.getText();
        int stock = Integer.parseInt(stockProductEdit_txt.getText());
        Double price = Double.valueOf(priceProductEdit_txt.getText());

        Product product = new Product(id, name, stock, price);
        if (daoProduct.insert(product) == -1){
            editProductMessage_lbl.setText("Operation failed!");
        }else{
            editProductMessage_lbl.setText("Product modified!");
        }
    }

    /**
     * This method creates all the changes needed to create an Order
     * in the Application
     * @param event Represents the clicking of the button whick
     *              triggers the method
     * @throws IOException It returns an exeption if failing
     */
    @FXML
    public void onMakeOrderButton(ActionEvent event) throws IOException {
        int quantity = Integer.parseInt(quantity_txt.getText());

        Client client = clientOrderTable.getSelectionModel().getSelectedItem();
        Product product = productOrderTable.getSelectionModel().getSelectedItem();

        int stock = ProductDAO.checkStock(product.getIdProduct());
        if (stock < quantity){
            successOrder_lbl.setText("Insuficient stock");
        }else{
            Order order = new Order(client.getIdClient()+1,client.getIdClient(), product.getIdProduct(), quantity);
            ProductDAO.updateStock(product.getIdProduct(), stock-quantity);
            if(daoOrder.insert(order) == -1){
                successOrder_lbl.setText("Operation failed");
            }else{
                successOrder_lbl.setText("Order made succesfully");
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Bill bill = new Bill(client.getName(), product.getName(), quantity, product.getPrice(), date);
            }
        }
    }
}