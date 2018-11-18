package com.example.racos2.jeonju_where_go;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import static com.example.racos2.jeonju_where_go.MainActivity.loc;

public class NextPage extends AppCompatActivity {    //implements TMapGpsManager.onLocationChangedCallback, TMapView.OnLongClickListenerCallback

    ImageView imageButton1, imageButton2, imageButton3, imageButton4, imageButton5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);

        imageButton1 = (ImageView) findViewById(R.id.tvTitle1);
        imageButton2 = (ImageView)findViewById(R.id.tvTitle2);
        imageButton3 = (ImageView)findViewById(R.id.tvTitle3);
        imageButton4 = (ImageView)findViewById(R.id.tvTitle4);
        imageButton5 = (ImageView)findViewById(R.id.tvTitle5);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "roco4";
                startActivity(new Intent(NextPage.this,RecomandActivity.class));
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "roco2";
                startActivity(new Intent(NextPage.this,RecomandActivity.class));
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "roco3";
                startActivity(new Intent(NextPage.this,RecomandActivity.class));
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "roco1";
                startActivity(new Intent(NextPage.this,RecomandActivity.class));
            }
        });
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "roco5";
                startActivity(new Intent(NextPage.this,RecomandActivity.class));
            }
        });

    }


    /** 뒤로 가기 버튼 선택될때*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();            //뒤로가기 기능
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /** 뒤로 가기 버튼 선택할때*/


    /** 뒤로가기 기능 오버라이드   */
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    /** 뒤로가기 기능 오버라이드   */




}



