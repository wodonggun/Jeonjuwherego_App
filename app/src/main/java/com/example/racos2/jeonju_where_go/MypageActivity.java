package com.example.racos2.jeonju_where_go;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.racos2.jeonju_where_go.MainActivity.log_in_off;
import static com.example.racos2.jeonju_where_go.MainActivity.user_email;

/**
 * Created by Administrator on 2017-07-29.
 */

public class MypageActivity extends AppCompatActivity {

    TextView myapge_textView,mypage_textView_email;



    //리사이클러뷰
    public static ArrayList<MypageActivity_Item> item;
    public static MypageActivity_adapter adapter;
    RecyclerView recyclerview2;
    TextView toolbar_textview2;

    RequestManager requestManager2;

    String url,name,url2,url3, address, like,link,menu,phone,time;
    Double Latitude,Longtitude;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();








    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        requestManager2 = Glide.with(MypageActivity.this);

        item = new ArrayList<>();



        Toolbar toolbar_mypage = (Toolbar) findViewById(R.id.toolbar_mypage);
        setSupportActionBar(toolbar_mypage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);


        myapge_textView = (TextView)findViewById(R.id.myapge_textView);
        myapge_textView.setText("마이페이지");

        mypage_textView_email = (TextView)findViewById(R.id.mypage_textView_email);


        if(log_in_off==true)
            mypage_textView_email.setText(user_email);
            //값 읽어오기전에 객체의 위치 설정해줘야됨.
            try {
                hiRef = database.getReference("유저").child(user_email.substring(0,user_email.length()-4)).child("cart");
            }
            catch(Exception e){
                hiRef = database.getReference();
            }


            if(log_in_off)
            hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.


                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        url  = snapShot.child("url1").getValue(String.class);
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

                        item.add(new MypageActivity_Item(MypageActivity.this,  url,url2,url3,name, address, like,Latitude,Longtitude,link,menu,phone,time));


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



        // 가로2개*세로 아이템  레이아웃
        recyclerview2 = (RecyclerView)findViewById(R.id.recyclerView2);
        recyclerview2.setOverScrollMode(View.OVER_SCROLL_NEVER);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MypageActivity.this, 2);
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setLayoutManager(layoutManager);
        recyclerview2.addItemDecoration(new MypageActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerview2.setItemAnimator(new DefaultItemAnimator());

        adapter = new MypageActivity_adapter(item, this, requestManager2);
        recyclerview2.setAdapter(adapter);

    }//온크리에이트 하단부


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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


   public void My_invalidate()
    {
        if (MypageActivity.item != null){
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

                        MypageActivity.item.add(new MypageActivity_Item(MypageActivity.this, url, url2, url3, name, address, like, Latitude, Longtitude, link, menu, phone, time));


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

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        hiRef = database.getReference();
//
//        if(Icart_count==-1)
//            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("count").setValue("0");
//            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("count").setValue(Scart_count);
//            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("url1").setValue("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/mypage_img.png?alt=media&token=55f094f4-9ab4-4846-aa14-49c4c927a618");
//        hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("name").setValue("[ 한바탕 전주 소개 ]");
//
//    }


}
