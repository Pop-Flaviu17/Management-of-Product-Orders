package com.example.orders_management.Model;

import java.util.Date;

public final record Bill(String clientName, String productName, int quantity, Double price, Date date) {

}
