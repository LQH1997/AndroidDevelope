package com.example.andrew.lab10;

/**
 * Created by Andrew on 2017/12/4.
 */

public class Info {
    private String name, birthday, gift;
    Info(String a, String b, String c) {
        name = a;
        birthday = b;
        gift = c;
    }

    public String getName() {
        return  name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGift() {
        return  gift;
    }

    void setName(String a) {
        name = a;
    }

    void setBirthday(String a) {
        birthday = a;
    }

    void setGift(String a) {
        gift = a;
    }

}
