package com.example.andrew.lab5;

import java.io.Serializable;

/**
 * Created by Andrew on 2017/10/18.
 */

public class ListItems implements Serializable {
    String ProductName;
    String Price;
    String Catagory;
    String Info;
    String src;
    int number;


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

    String getSrc() {return src; }

    int getNum() {return  number;}

    void setAttr(String name, String price, String cata, String info, String s, int _number) {
        this.Catagory = cata;
        this.ProductName = name;
        this.Price = price;
        this.Info = info;
        this.src = s;
        this.number = _number;
    }

    void setNum(int a) {
        this.number = a;
    }
}


