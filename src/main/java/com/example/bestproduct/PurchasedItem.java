// **********************************************************************************
// Title: BestProduct
// Author: Carly Shearer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: PurchasedItem.java
// Description: Class creates subclass of Item called PurchasedItem with attribute
// rating.
// **********************************************************************************
package com.example.bestproduct;
import java.io.*;
public class PurchasedItem extends Item implements Serializable{
    private String rating;

    public PurchasedItem() {
        this.rating = rating;
    }

    public PurchasedItem(String name, double price, String quality, boolean available, String rating) {
        super(name, price, quality, available);
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
        return super.toString() + "\tPersonal rating: " + rating;
    }
}
