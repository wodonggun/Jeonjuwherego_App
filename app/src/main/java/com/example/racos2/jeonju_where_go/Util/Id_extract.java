package com.example.racos2.jeonju_where_go.Util;

/**
 * Created by Racos7 on 2017-07-29.
 */

public class Id_extract {

    String temp;
    int indexOf;
    Id_extract(String s){
      temp = s;
    }

    public String My_extract(String s2){

        indexOf = s2.indexOf("@");
       return(temp.substring(0,indexOf));

    }



}
