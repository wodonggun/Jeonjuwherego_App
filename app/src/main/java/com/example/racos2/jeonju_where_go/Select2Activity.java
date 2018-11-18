package com.example.racos2.jeonju_where_go;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.racos2.jeonju_where_go.Util.ProgressDlg;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.racos2.jeonju_where_go.MainActivity.info;
import static com.example.racos2.jeonju_where_go.MainActivity.loc;
import static com.example.racos2.jeonju_where_go.MainActivity.location;
import static com.example.racos2.jeonju_where_go.MainActivity.user_email;
import static com.example.racos2.jeonju_where_go.R.id.recyclerView;


/**
 * Created by Racos7 on 2017-07-22.
 */

public class Select2Activity extends AppCompatActivity {

    //파이어베이스
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();




    //로딩 프로그래스바
    private AsyncTask<Void, Void, Void> mProgressDlg;






    //리사이클뷰
    private ArrayList<Item> item;
    private My_adapter adapter;
    RecyclerView recyclerview;
    TextView toolbar_textview2;




    //글라이드
    RequestManager requestManager;
    String url,name,url2,url3, address, like,link,menu,phone,time;
    Double Latitude,Longtitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(info);      //액션바(툴바) 이름 변경


        toolbar_textview2 = (TextView)findViewById(R.id.toolbar_textview2);
        toolbar_textview2.setText(loc+ " ▶ "+ info);


        //온크리에이트
        requestManager = Glide.with(Select2Activity.this);


        //리사이클러뷰
        item = new ArrayList<>() ;


        //로딩 프로그래스바
       mProgressDlg = new ProgressDlg(Select2Activity.this).execute();































        recyclerview = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layout);



        adapter = new My_adapter(item, this,requestManager);
        recyclerview.setAdapter(adapter);



    }//onCreate 하단부

@Override
public void onResume() {
    super.onResume();

    item.clear();

     //로딩 프로그래스바
     mProgressDlg = new ProgressDlg(Select2Activity.this).execute();



    hiRef = database.getReference(info).child(loc);
    if(loc!="모든 지역")
        hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {



                    url = snapShot.child("url1").getValue(String.class);

                    url2 = snapShot.child("url2").getValue(String.class);
                    url3 = snapShot.child("url3").getValue(String.class);
                    name = snapShot.child("name").getValue(String.class);

                    address = snapShot.child("address").getValue(String.class);

                    like = snapShot.child("like").getValue(String.class);

                    Latitude = snapShot.child("Latitude").getValue(Double.class);
                    Longtitude = snapShot.child("Longtitude").getValue(Double.class);
                    link = snapShot.child("link").getValue(String.class);
                    menu = snapShot.child("menu").getValue(String.class);
                    phone = snapShot.child("phone").getValue(String.class);
                    time = snapShot.child("time").getValue(String.class);

                    item.add(new Item(Select2Activity.this,url,url2,url3,name,address, like,Latitude,Longtitude,link,menu,phone,time));


                }
                  adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("read error");
            }
        });

    else {
        for (int i = 0; i < 2; i++) {
            hiRef = database.getReference(info).child(location[i]);
            hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.

                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        adapter.notifyDataSetChanged();

                        url = snapShot.child("url1").getValue(String.class);

                        url2 = snapShot.child("url2").getValue(String.class);
                        url3 = snapShot.child("url3").getValue(String.class);
                        name = snapShot.child("name").getValue(String.class);

                        address = snapShot.child("address").getValue(String.class);

                        like = snapShot.child("like").getValue(String.class);

                        Latitude = snapShot.child("Latitude").getValue(Double.class);
                        Longtitude = snapShot.child("Longtitude").getValue(Double.class);
                        link = snapShot.child("link").getValue(String.class);
                        menu = snapShot.child("menu").getValue(String.class);
                        phone = snapShot.child("phone").getValue(String.class);
                        time = snapShot.child("time").getValue(String.class);

                        item.add(new Item(Select2Activity.this, url,url2,url3,name, address, like,Latitude,Longtitude,link,menu,phone,time));

                    }
                    adapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("read error");
                }
            });
        }
    }









        if (MypageActivity.item != null) {
            MypageActivity.item.clear();

            hiRef = database.getReference("유저").child(user_email.substring(0, user_email.length() - 4)).child("cart");

            hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.


                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        url = snapShot.child("url1").getValue(String.class);
                        url2 = snapShot.child("url2").getValue(String.class);
                        url3 = snapShot.child("url3").getValue(String.class);
                        name = snapShot.child("name").getValue(String.class);
                        address = snapShot.child("address").getValue(String.class);
                        like = snapShot.child("like").getValue(String.class);
                        Latitude = snapShot.child("Latitude").getValue(Double.class);
                        Longtitude = snapShot.child("Longtitude").getValue(Double.class);
                        link = snapShot.child("link").getValue(String.class);
                        menu = snapShot.child("menu").getValue(String.class);
                        phone = snapShot.child("phone").getValue(String.class);
                        time = snapShot.child("time").getValue(String.class);

                        MypageActivity.item.add(new MypageActivity_Item(Select2Activity.this, url, url2, url3, name, address, like, Latitude, Longtitude, link, menu, phone, time));


                    }
                    try {
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("notifydatesetchanged에러", "확인바람");

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("read error");
                }
            });
        }

}




    @Override
    public void onStart(){
         super.onStart();
    }
    @Override
    public void onRestart() {
        super.onRestart();
    }
    @Override
    public void onPause(){ super.onPause();}
    @Override
    public void onStop()
    {
        super.onStop();
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

