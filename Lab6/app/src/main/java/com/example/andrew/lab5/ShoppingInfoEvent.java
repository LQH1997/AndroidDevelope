package com.example.andrew.lab5;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/27.
 */

public class ShoppingInfoEvent {
    public  ListItems it;
    public ShoppingInfoEvent(ListItems items) {
        it = items;
    }

    public  ListItems getInfo() {
        return  it;
    }
}
