package com.example.andrew.lab11;

import java.io.Serializable;

/**
 * Created by Andrew on 2017/12/11.
 */

public class Info implements Serializable {
    private String login;
    private String id;
    private String blog;

    public Info() {
        login = "NULL";
        id = "NULL";
        blog = "NULL";
    }

    public Info(String a, String b, String c) {
        login = a;
        id = b;
        blog = c;
    }

    public void setLogin(String a) {
        login = a;
    }

    public void setId(String a) {
        id = a;
    }

    public void setBlog(String a) {
        blog = a;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getBlog() {
        return blog;
    }
}
