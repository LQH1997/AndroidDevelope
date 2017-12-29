package com.example.andrew.final_term;

import android.media.Image;
import android.provider.ContactsContract;

/**
 * Created by Andrew on 2017/12/24.
 */

public class Info {
    public String previewImage;
    public String title;
    public String context;
    public String time;

    Info(String a, String b, String c, String d) {
        previewImage = a;
        title = b;
        context = c;
        time = d;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public String getTime() {
        return time;
    }

}
