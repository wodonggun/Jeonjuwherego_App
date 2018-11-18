package com.example.racos2.jeonju_where_go.Util;

/**
 * Created by Racos7 on 2017-07-25.
 */

public class  TranslateActivity {
    String myloc;

    TranslateActivity(String loc){
        if(loc.equals("청주"))
           this.myloc = "청주";
        if(loc.equals("고산"))
            this.myloc ="고산";
        if(loc.equals("단양"))
            this.myloc ="단양";
        if(loc.equals("보은"))
            this.myloc ="보은";
        if(loc.equals("옥천"))
            this.myloc ="옥천";
        if(loc.equals("음성"))
            this.myloc ="음성";
        if(loc.equals("제천"))
            this.myloc ="제천";
        if(loc.equals("증평"))
            this.myloc ="증평";
        if(loc.equals("진천"))
            this.myloc ="진천";
        if(loc.equals("영동"))
            this.myloc ="영동";
        if(loc.equals("충주"))
            this.myloc ="충주";
        if(loc.equals("그 외"))
            this.myloc ="그 외";


    }

    String mySelectloc(){
        return myloc;
    }




    }





