package com.example.racos2.jeonju_where_go;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static com.example.racos2.jeonju_where_go.MainActivity.info;
import static com.example.racos2.jeonju_where_go.MainActivity.loc;


/**
 * Created by Racos7 on 2017-07-22.
 */

public class SelectActivity extends AppCompatActivity {

    ImageView select1_imageView0,select1_imageView1,select1_imageView2,select1_imageView3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);




        TextView toolbar_textview = (TextView)findViewById(R.id.toolbar_textview);
        toolbar_textview.setText(loc+ " ▶ ");






        select1_imageView0 = (ImageView)findViewById(R.id.select1_imageView0);
        select1_imageView1 = (ImageView)findViewById(R.id.select1_imageView1);
        select1_imageView2 = (ImageView)findViewById(R.id.select1_imageView2);
        select1_imageView3 = (ImageView)findViewById(R.id.select1_imageView3);







        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/select1_img%2Fselec_realsocrisan.jpg?alt=media&token=82669ad9-e877-4b9d-a084-0a1e79b9b6c1").into(select1_imageView3);


        /** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ버튼 클릭시 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/


        select1_imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info ="숙박";
                startActivity(new Intent(SelectActivity.this,Select2Activity.class));
            }
        });

        select1_imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info ="음식";

                startActivity(new Intent(SelectActivity.this,Select2Activity.class));
            }
        });

        select1_imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info ="축제";
                startActivity(new Intent(SelectActivity.this, Select2Activity.class));
            }
        });

        select1_imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info ="관광";
                startActivity(new Intent(SelectActivity.this, Select2Activity.class));
            }
        });



    }//onCreate 하단부




//    /** 뒤로 가기 버튼 선택될때*/
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id) {
//            case android.R.id.home:
//                onBackPressed();            //뒤로가기 기능
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//    /** 뒤로 가기 버튼 선택할때*/





    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            // 예외사항 처리
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resouorce, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            // 이미지 로드 완료됬을 때 처리
            return false;
        }
    };

//    /** 뒤로가기 기능 오버라이드   */
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//    }
//    /** 뒤로가기 기능 오버라이드   */




}
