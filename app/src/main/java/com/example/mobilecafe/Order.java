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

    public Product get(int position){
        return order.get(position);
    }
    public int size(){
        return order.size();
    }

    public double totalPrice(){
        double sum = 0.0;
        for (Product p: order) {
            sum+=Double.valueOf(p.getPrice());
        }
        return sum;
    }
}
