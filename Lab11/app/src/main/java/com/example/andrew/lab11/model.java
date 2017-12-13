package com.example.andrew.lab11;

/**
 * Created by Andrew on 2017/12/13.
 */

public class model {
    public String name;
    public String language;
    public String description;

    public model(String a, String b, String c) {
        name = a;
        language = b;
        description = c;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String a) {
        description = a;
    }


}
