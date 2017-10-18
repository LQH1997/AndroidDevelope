package com.example.andrew.lab5;

/**
 * Created by Andrew on 2017/10/18.
 */

public class ListItems {
    String ProductName;
    String Price;
    String Catagory;
    String Info;

    String getName() {
        return ProductName;
    }

    String getPrice() {
        return Price;
    }

    String getCatagory() {
        return  Catagory;
    }

    String getInfo() {
        return Info;
    }

    void setAttr(String name, String price, String cata, String info) {
        this.Catagory = cata;
        this.ProductName = name;
        this.Catagory = cata;
        this.Info = info;
    }
}


