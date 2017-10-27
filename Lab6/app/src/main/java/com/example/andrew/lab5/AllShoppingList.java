package com.example.andrew.lab5;

import java.util.ArrayList;

/**
 * Created by Andrew on 2017/10/27.
 */

public class AllShoppingList {
        public ArrayList<ListItems> it;
        public AllShoppingList(ArrayList<ListItems> items) {
            it = items;
        }

        public  ArrayList<ListItems> getInfo() {
            return  it;
        }
}
