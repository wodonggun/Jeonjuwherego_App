package com.example.racos2.jeonju_where_go.Activity;

/**
 * Created by Racos2 on 2017-12-23.
 */

public class Obj_Review {
    private String body,date,title,url,writer,key;

    void obj_Review(){
        body="hi~hi";
        date = "2017.12.35 15:30";
        title="titi";
        url = "www.naver.com";
        writer ="wodonggun@naver.com";
    }
    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getWriter() {
        return writer;
    }

    public String getKey() {
        return key;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
