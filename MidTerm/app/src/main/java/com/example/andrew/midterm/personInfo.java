package com.example.andrew.midterm;

import java.io.Serializable;

/**
 * Created by Andrew on 2017/11/18.
 */

public class personInfo implements Serializable {
    private int id;
    private String name;       //名字
    private String gender;     //性别
    private String birthDate;  //出生年月日
    private String homeTown;   //籍贯
    private String shili;      //势力
    private String personPic;  //图像名称
    public personInfo(int id, String _name, String _gender, String _birthDate, String _homeTown, String _shili, String _personPic) {
        this.id = id;
        this.name = _name;
        this.gender = _gender;
        this.birthDate = _birthDate;
        this.homeTown = _homeTown;
        this.shili = _shili;
        this.personPic = _personPic;
    }

    public personInfo() {
        this.id = -1;
        this.name = "aaa";
        this.gender = "aaa";
        this.birthDate = "aaa";
        this.homeTown = "aaa";
        this.shili = "aaa";
        this.personPic = "aaa";
    }

    public int getId() { return this.id; }

    public String getName() {
        return this.name;
    }

    public  String getGender() {
        return  this.gender;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public String getHomeTown() {
        return this.homeTown;
    }

    public String getShili() {
        return this.shili;
    }

    public String getPersonPic() {
        return this.personPic;
    }

    public void setName(String a) {
        this.name = a;
    }

    public void setGender(String a) {
        this.gender = a;
    }

    public void setBirthDate(String a) {
        this.birthDate = a;
    }

    public void setShili(String a) {
        this.shili = a;
    }

    public void setPersonPic(String a) {
        this.personPic = a;
    }
}
