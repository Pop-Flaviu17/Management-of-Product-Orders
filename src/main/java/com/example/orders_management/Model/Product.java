package com.example.orders_management.Model;

public class Product {
    private Integer idProduct;
    private String name;
    private Integer stock;
    private Double price;
    public Product(){

    }
    public Product (int idProduct, String name, Integer stock, Double price){
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.idProduct = idProduct;
    }
    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
