package com.example.racos2.jeonju_where_go;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Racos7 on 2017-07-22.
 */

public class Item implements Serializable{


    String image,image2,image3;
    String name,address,like,link,menu,phone,time;                    //타이틀==네임
    Double Longtitude,Latitude;


        //context는 지금사용안하지만 나중에 드로워블 가져올경우에
    Item(Context context, String image,String image2,String image3, String name, String address, String like,Double Latitude,Double Longtitude,String link,String menu,String phone,String time){
        if(image!=null)
            this.image = image;
        else      //이미지가 없으면 서버에 no_img파일 링크
            this.image="https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/no_img.png?alt=media&token=0bce3c97-ce9d-4a9e-b5bb-69d9939595b0";

        this.image2 = image2;
        this.image3 = image3;
        this.name = name;
        this.address = address;
        this.like = like;

        this.Latitude=Latitude;
        this.Longtitude =Longtitude;
        this.link = link;
        this.menu =menu;
        this.phone =phone;
        this.time =  time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }


    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getLike() {return like; }

    public void setLike(String like) {
        this.like = like;
    }


    public String getName() {
        return name;
    }

    public void getName(String name) {
        this.name = name;
    }


    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }

    public Double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(Double Longtitude) {
        this.Longtitude = Longtitude;
    }

    public String getlink() {
        return link;
    }

    public void setlink(String link) {
        this.link = link;
    }

    public String getmenu() {
        return menu;
    }

    public void setmenu(String menu) {
        this.menu = menu;
    }



    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }


}
