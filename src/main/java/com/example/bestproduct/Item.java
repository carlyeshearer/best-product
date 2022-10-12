// **********************************************************************************
// Title: BestProduct
// Author: Carly Shearer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: Item.java
// Description: Class creates an Item object with attributes name, price, quality,
// and available.
// **********************************************************************************
package com.example.bestproduct;
import java.io.*;
import java.text.DecimalFormat;
public class Item implements Serializable {
    private String name;
    private double price;
    private String quality;
    private boolean available;

    public Item(){
    }

    public Item(String name, double price, String quality, boolean available) {
        this.name = name;
        this.price = price;
        this.quality = quality;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    //equals() method
    @Override
    public boolean equals(Object i){
        if(i == this) {
            return true;
        }
        else if(i instanceof Item) {
            return true;
        }
        else{
            return false;
        }
    }

    //toString() method
    @Override
    public String toString() {
        String message = "";
        DecimalFormat formatter = new DecimalFormat("#0.00");

        if(this.available == true){
            message += "Name: " + name + "\tPrice: " + "$" + formatter.format(price) + "\tQuality:  " + quality + "\tIs available.";
        }
        else{
            message += "Name: " + name + "\tPrice: " + "$" + formatter.format(price) + "\tQuality:  " + quality + "\tIs not available.";
        }

        return message;
    }
}
