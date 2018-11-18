package com.example.racos2.jeonju_where_go.Activity;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-29.
 */

public class Item_Review implements Serializable{
    String title,body,date,url,writer;

    Item_Review(Context context, String title, String body, String date, String url, String writer){
        this.title = title;
        this.body = body;
        this.date = date;
        this.url = url;
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
