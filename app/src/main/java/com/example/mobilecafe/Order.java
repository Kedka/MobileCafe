package com.example.mobilecafe;

import java.util.LinkedList;
import java.util.List;

public class Order {

    private LinkedList<Product> order;

    Order(){
        order = new LinkedList<Product>();
    }

    public void addToOrder(Product p){
        order.add(p);
    }
}
