package com.example.racos2.jeonju_where_go;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.racos2.jeonju_where_go.R.id.sign_in_button10;


//구글 연동 sign_in 버튼


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    //테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.
    //테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.
    //테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.
    //테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.
    //테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.//테스트를 합니다.


    //로그인 정보
    static String user_ID,user_email,user_nickname,user_imgurl,
            user_tour_img,user_tour_name,user_tour_address;
    static String user_password = "초기값0000";
    static boolean log_in_off = false;
    private static int mMarkerID = 0;

    public static int Icart_count;
    public static String Scart_count;


    //유저 정보
    static String loc ="";              //어떤 지역 선택했는지
    static String info ="";             //어떤 정보 선택했는데


    //뒤로가기 변수선언
    BackPressCloserHandler goBack = new BackPressCloserHandler(this);



    //글라이드
    String profile_image_url;
    ImageView imageView_profile;
    View view;


    //파이어베이스
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //각종 변수값
    private DrawerLayout mDrawerLayout;
    Button btn,selecpagebtn,btn_kakaolink;
    TextView TextView_Drawer_ID;
    String url,name,url2,url3, address, like,link,menu,phone,time;
    Double Latitude,Longtitude;


    static String[] location = new String[2];
    static String[] info_arr = new String[4];
    ImageView    main_imageView1,main_imageView2,main_imageView3,main_imageView4,main_imageView5,main_imageView6;




    //구글 연동 변수
    Button signOutButton;
    SignInButton signInButton;      //이거랑  sign_in_button랑 같은거임.
    GoogleApiClient mgoogleApiClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;


    //플리퍼
    ViewFlipper Flipper; //Flipper
    ViewFlipper Flipper2; //Flipper




    //메인 지역 버튼
    ImageView imageView_loc1,imageView_loc2,imageView_loc3,imageView_loc4,imageView_loc5,imageView_loc6,imageView_loc7,imageView_loc8
            ,imageView_loc9,imageView_loc10,imageView_loc11,imageView_loc12;





//메인 티맵
    TMapView mainTmap;
    TMapGpsManager maingps;
    TMapPoint mainPoint;
    TMapPoint MarkerPoint;
    TMapMarkerItem markeritem;
    TMapMarkerItem markerItem2;
    int i;
    double longti, lati;
    String jeonjuname;
    Bitmap bitmap2;
    Bitmap src;

    private String mainapiKey = "1c9faf4b-9050-31c3-8c7f-7089192b6e67";

    Context mContext = null;

    double mylongtitude, mylatitude;
    double MarkerPointLong, MarkerPointLati;

    //티맵 마커 아이템 저장
    private ArrayList<Item> item2;
    private My_adapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RelativeLayout RelaTmap = (RelativeLayout)findViewById(R.id.main_map);
        TMapView tmapview = new TMapView(this);

        tmapview.setSKTMapApiKey("2ce1b26f-7142-429b-8b04-192cc812e945");

        tmapview.setCompassMode(true);
        tmapview.setIconVisibility(true);
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        RelaTmap.addView(tmapview);


        //초기화 변수 모아놓기
        loc = "";
        info = "";
        user_email = "default";
        Icart_count = -1;

        //지역 초기화
        location[0]= "덕진구";
        location[1]= "완산구";
        info_arr[0]= "관광";
        info_arr[1]= "숙박";
        info_arr[2]= "음식";
        info_arr[3]= "축제";



        item2 = new ArrayList<>() ;

        for(int i=0;i<4;i++)
            for(int j=0;j<2;j++){
            hiRef = database.getReference(info_arr[i]).child(location[j]);
            hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.


                @Override

                public void onDataChange(DataSnapshot dataSnapshot) {
                        int k =0;
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


                        item2.add(new Item(MainActivity.this, url, url2, url3, name, address, like, Latitude, Longtitude, link, menu, phone, time));



                    }
                    try {

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






        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ마커 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
/**
        mainTmap.setOnEnableScrollWithZoomLevelListener(new TMapView.OnEnableScrollWithZoomLevelCallback() {
            @Override
            public void onEnableScrollWithZoomLevelEvent(float v, TMapPoint tMapPoint) {
                if(v>17){
                    System.out.println("ss");
                    markeritem.setCanShowCallout(true);
                }
                else{
                    System.out.println("sssss");
                    markeritem.setCanShowCallout(false);
                }
            }
        });
*/
        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ메인 지도 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        // 메인 이미지뷰 6개
        main_imageView1 = (ImageView)findViewById(R.id.main_imageView1);
        main_imageView2 = (ImageView)findViewById(R.id.main_imageView2);
        main_imageView3 = (ImageView)findViewById(R.id.main_imageView3);
        main_imageView4 = (ImageView)findViewById(R.id.main_imageView4);
        main_imageView5 = (ImageView)findViewById(R.id.main_imageView5);
        main_imageView6 = (ImageView)findViewById(R.id.main_imageView6);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_bibimbap1.jpg?alt=media&token=f6d74d40-1ff0-4703-8279-c43ab9b1353b").into(main_imageView1);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_gallery_hanok2.jpg?alt=media&token=98f66ea6-bd20-4ba7-bed2-101dac91c1cf").into(main_imageView2);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_gallery_byukhwa3.jpg?alt=media&token=defc5dab-fb06-40eb-a61c-40fda8e9a3a3").into(main_imageView3);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_gallery_hanok4.jpg?alt=media&token=7b517367-3e2a-4483-9cbc-dbafc89a80f9").into(main_imageView4);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_gallery_gung5.jpg?alt=media&token=e266fe15-2391-43f8-b7fc-e85979073018").into(main_imageView5);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/main_ImageView%2Fmain_gallery_nambu6.jpg?alt=media&token=f4195dee-fc38-47aa-a106-873044a8107b").into(main_imageView6);


        main_imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/tp/content/view.jsp?cid=2300201&type=G&listType=SPC&orderType=R&pageNum=1&category=R";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });

        main_imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/tp/content/view.jsp?cid=2300936&type=C&listType=SPC&orderType=R&pageNum=1&category=T";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });

        main_imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/tp/content/view.jsp?cid=2300935&listType=SPC&category=T";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });

        main_imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/w_stay/w_stay_view.jsp?cid=2458330";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });

        main_imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/where/where_main_search.jsp?cid=1981291&areaCode=37&sigunguCode=12&catAll3=all";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });

        main_imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String homepage = "http://korean.visitkorea.or.kr/kor/bz15/tp/content/view.jsp?cid=2300675&listType=SPC&category=T";
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });







        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();

/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ플리퍼ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        Flipper = (ViewFlipper) findViewById(R.id.Flipper);//Flipper 객체

        Flipper2 = (ViewFlipper)findViewById(R.id.Flipper2);

        Animation showln = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Flipper.setInAnimation(showln);
        Flipper2.setInAnimation(showln);
        Animation showout = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        Flipper.setOutAnimation(showout);
        Flipper2.setOutAnimation(showout);

        Flipper.setFlipInterval(2500);
        Flipper.startFlipping();
        Flipper2.setFlipInterval(3500);
        Flipper2.startFlipping();
/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ플리퍼ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/








        /** ---- 지역 아이콘 누르면 6개정보(select_activity)로 이동 ----- */
       imageView_loc1 = (ImageView) findViewById(R.id.imageButton_loc1);
       imageView_loc2 = (ImageView) findViewById(R.id.imageButton_loc2);


        imageView_loc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "덕진구";
                startActivity(new Intent(MainActivity.this,SelectActivity.class));
            }
        });


        imageView_loc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "완산구";
                startActivity(new Intent(MainActivity.this,SelectActivity.class));
            }
        });


/** ---- 지역 아이콘 누르면 6개정보(select_activity)로 이동  종단점----- */








        //카카오 연동
        btn_kakaolink = (Button)findViewById(R.id.btn_kakaolink);
        btn_kakaolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // shareKakao(btn_kakaolink);
            }
        });









//
//        //데이터베이스 연동
//        btn = (Button)findViewById(R.id.button1);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hiRef = database.getReference();
//                hiRef.child("김밥나라").setValue("김밥 : 2000원");
//
//            }
//        });












        //도로어
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setPopupTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);







        //글라이드
        view= navigationView.getHeaderView(0);     // (drawer_header.xml을 가져오기위해서+ imageView_profile추가위해서)
        imageView_profile = (ImageView)view.findViewById(R.id.imageView_profile);










        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(false); //드로어 내부의 menuItem클릭했을때 클릭했던 텍스트는 회색글씨로 바꿔줌.
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_attachment:
                        loc = "";           //초기화
                        info = "";
                        Toast.makeText(getApplicationContext(), "홈화면 초기화", Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.navigation_item_images:
                        startActivity(new Intent(MainActivity.this,MypageActivity.class));

                        break;

                    case R.id.nav_sub_menu_item01:
                        loc = "모든 지역";
                        info = "관광";
                        startActivity(new Intent(MainActivity.this,Select2Activity.class));

                        break;

                    case R.id.nav_sub_menu_item02:
                        loc = "모든 지역";
                        info = "숙박";
                        startActivity(new Intent(MainActivity.this,Select2Activity.class));
                        break;
                    case R.id.nav_sub_menu_item03:

                        loc = "모든 지역";
                        info = "음식";
                        startActivity(new Intent(MainActivity.this,Select2Activity.class));
                        break;
                    case R.id.nav_sub_menu_item04:
                        loc = "모든 지역";
                        info = "축제";
                        startActivity(new Intent(MainActivity.this,Select2Activity.class));
                        break;

                    case R.id.nav_sub_menu_item05:
                        loc = "모든 지역";
                        info = "추천코스";
                        startActivity(new Intent(MainActivity.this,NextPage.class));
                        break;
                }

                return true;
            }
        });//navigationView.setNavigationItemSelectedListener 네비게이션 하단










        //구글 연동
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this  )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        View nav_header = navigationView.getHeaderView(0);
        signInButton = (SignInButton)nav_header.findViewById(R.id.sign_in_button10);
        signInButton.setOnClickListener(this);
        signOutButton = (Button)nav_header.findViewById(R.id.signOutButton10);
        signOutButton.setOnClickListener(this);


        TextView_Drawer_ID = (TextView)nav_header.findViewById(R.id.textView_id);



    }//온크리트 하단








//    //카카오 연동 클래스
//    public void shareKakao(View v) {
//        try {
//            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
//
//            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
//
//            kakaoBuilder.addText("카카오링크 테스트\n"+"우리같이 놀아볼까요?");
//
//            String url = "https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%A7%81%ED%81%AC%2Fimg_link.png?alt=media&token=535040d0-e1f9-4163-a870-20195d291a36";
//            kakaoBuilder.addImage(url, 720, 1024);
//
//            kakaoBuilder.addAppButton("링크 연결하기");
//
//            kakaoLink.sendMessage(kakaoBuilder.build(), this);
//
//
//        } catch (KakaoParameterException e) {
//            e.printStackTrace();
//        }
//    }







    //드로어 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //드로어 셀렉
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }









    //구글 연동 implement
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case sign_in_button10:
                signIn();
                break;

            case R.id.signOutButton10:
                signOut();
                TextView_Drawer_ID.setVisibility(INVISIBLE);
                break;
        }

    }
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(mgoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(log_in_off!=false) {
                    Toast.makeText(getApplicationContext(), "로그아웃 완료", Toast.LENGTH_SHORT).show();
                    log_in_off = false;
                    user_ID = "default";
                    user_email="default";
                    imageView_profile.setImageResource(R.drawable.img2);
                    signInButton.setVisibility(VISIBLE);
                }
                else
                    Toast.makeText(getApplicationContext(), "로그인 해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{

        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {                                               //로그인 성공시
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            log_in_off = true;
            user_nickname = getString(R.string.signed_in_fmt, acct.getDisplayName()).substring(0,getString(R.string.signed_in_fmt,acct.getDisplayName()).length()-9);
            TextView_Drawer_ID.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            user_imgurl = getString(R.string.signed_in_fmt,acct.getPhotoUrl()).substring(0,getString(R.string.signed_in_fmt,acct.getPhotoUrl()).length()-9);
            profile_image_url = user_imgurl;

            user_email =  getString(R.string.signed_in_fmt,acct.getEmail()).substring(0,getString(R.string.signed_in_fmt,acct.getEmail()).length()-9);



            //유저 아이디 추출
            int indexOf = user_email.indexOf("@");
            user_ID = user_email.substring(0,indexOf);
            //System.out.println(user_ID);
            Glide.with(this).load(profile_image_url).into(imageView_profile);

            //로그인 객체 생성.
            Firebase_CheckDB hi = new Firebase_CheckDB();
             updateUI(true);
        }
        else {  //로그인 실패시
                // Signed out, show unauthenticated UI.
            log_in_off = false;
            updateUI(false);
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(sign_in_button10).setVisibility(GONE);
            Toast.makeText(getApplicationContext(), "로그인 성공.", Toast.LENGTH_SHORT).show();
            hiRef = database.getReference();
            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("count").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Scart_count = dataSnapshot.getValue(String.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );



            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("count").setValue("0");
            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("url1").setValue("https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/mypage_img.png?alt=media&token=55f094f4-9ab4-4846-aa14-49c4c927a618");
            hiRef.child("유저").child( user_email.substring(0,user_email.length()-4)).child("cart").child("TotalCount").child("name").setValue("[ 한바탕 전주 소개 ]");



            //나중에 로그인 아이디,비번,이메일 값 세팅할때
           /* hiRef.child("유저").child(user_ID).child("ID").setValue(user_ID);
            hiRef.child("유저").child(user_ID).child("email").setValue(user_email);
            hiRef.child("유저").child(user_ID).child("password").setValue(user_password);*/


            TextView_Drawer_ID.setVisibility(VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "로그인 실패.", Toast.LENGTH_SHORT).show();
            findViewById(sign_in_button10).setVisibility(VISIBLE);

            TextView_Drawer_ID.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), ""+connectionResult, Toast.LENGTH_SHORT).show();
    }








    //뒤로가기 버튼 오버라이딩
    @Override
    public void onBackPressed(){
        goBack.onBackPressed();

    }



    //메인플리버
    public void mainonclick(View v) {
        switch (v.getId()) {
            case R.id.mainbtn1:
                Flipper.stopFlipping();
                Flipper.showPrevious();
                Flipper.setFlipInterval(2500); // 1초에
                Flipper.startFlipping(); // 한번씩 Flipping
                break;
            case R.id.mainbtn2:
                Flipper.stopFlipping();
                Flipper.showNext();
                Flipper.setFlipInterval(2500); // 1초에
                Flipper.startFlipping(); // 한번씩 Flipping
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }
    }



    private void signInAnonymously(){
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("hi", "signInAnonymously:FAILURE", exception);
                    }
                });


    }










}//클래스 끝>>





