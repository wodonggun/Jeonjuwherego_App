package com.example.racos2.jeonju_where_go.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.racos2.jeonju_where_go.R;
import com.example.racos2.jeonju_where_go.Util.Adapter_Review;
import com.example.racos2.jeonju_where_go.Util.ProgressDlg;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Activity_Review extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_Review adapter;
    private RequestManager requestManager;
    private FloatingActionButton floatingActionButton;
    private StorageReference storageReference;
    private ArrayList<Item_Review> item;

    //파이어베이스
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();
    private String title,body,date,url,writer;
    private AsyncTask<Void, Void, Void> mProgressDlg;

    public static String name,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");


        requestManager = Glide.with(Activity_Review.this);
        hiRef = database.getReference("게시판").child(name);
        item = new ArrayList<>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.backicon);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView toolbar_textview = (TextView)findViewById(R.id.toolbar_textview);
        toolbar_textview.setText("  리뷰");




        recyclerView = (RecyclerView)findViewById(R.id.review_recycler);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.btn_float);
        floatingActionButton.setOnClickListener(listener);


        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Activity_Review.this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new Adapter_Review(item, Activity_Review.this, requestManager);
        recyclerView.setAdapter(adapter);


        // 데이터 베이스에서 저장한 내용 가져오기












    }

    @Override
    protected void onResume() {
        super.onResume();



        // 아이템 add 시키기
        item.clear();
        mProgressDlg = new ProgressDlg(Activity_Review.this).execute();
        hiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    title = snapShot.child("title").getValue(String.class);
                    body = snapShot.child("body").getValue(String.class);
                    date = snapShot.child("date").getValue(String.class);
                    url = snapShot.child("url").getValue(String.class);
                    writer = snapShot.child("writer").getValue(String.class);
                    item.add(new Item_Review(Activity_Review.this, title, body, date, url, writer));


                }
                try {
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.e("review notifihanged에러", "확인");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("error","파이어베이스 읽기 실패");

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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_float:
                    if(!email.equals("default")) {
                        Intent intent = new Intent(Activity_Review.this, Activity_Review_Write.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        break;
                    }
                    else
                        Toast.makeText(getApplicationContext(),"로그인 먼저 해주세요.",Toast.LENGTH_SHORT).show();
            }
        }
    };

}
