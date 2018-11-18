package com.example.racos2.jeonju_where_go.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.racos2.jeonju_where_go.R;

import static com.example.racos2.jeonju_where_go.Activity.Activity_Review.name;


public class Activity_Review_Detail extends AppCompatActivity {
    private Item_Review item_review;
    private String title, content, url,name_title;
    private TextView tv_name,tv_body,tv_title,tv_writer,tv_date;
    private ImageView imageView_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        //activity_review_detail 레이아웃 만드세요

        Intent intent = getIntent();
        item_review = (Item_Review)intent.getSerializableExtra("review_data");
        name_title = "["+" "+name+" "+"]";

        //세팅 하면 끝
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_body = (TextView)findViewById(R.id.tv_body);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_writer = (TextView)findViewById(R.id.tv_writer);
        tv_date = (TextView)findViewById(R.id.tv_date);
        imageView_url = (ImageView)findViewById(R.id.imageView_url);

        tv_name.setText(name_title);
        tv_body.setText(item_review.getBody());
        tv_title.setText(item_review.getTitle());
        tv_writer.setText(item_review.getWriter());
        tv_date.setText(item_review.getDate());

//        if(item_review.getTitle().length()>=12)
//        tv_title.setLayoutParams(new RelativeLayout.LayoutParams(2000,300));
//        else
//            ;

        Glide.with(this).load(item_review.getUrl()).into(imageView_url);

    }
}