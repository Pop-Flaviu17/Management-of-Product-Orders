package com.example.orders_management.Model;

public class Order {
    private Integer idOrder;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;
    public Order(){

    }
    public Order(Integer idOrder, Integer clientId, Integer productId, Integer quantity){
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.idOrder = idOrder;
    }
    public int getIdOrder() {
        return idOrder;
    }
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
