package com.racos.racos2.jeonju_where_go;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.racos.racos2.jeonju_where_go.Activity.Activity_Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import static com.racos.racos2.jeonju_where_go.MainActivity.Icart_count;
import static com.racos.racos2.jeonju_where_go.MainActivity.info;
import static com.racos.racos2.jeonju_where_go.MainActivity.loc;
import static com.racos.racos2.jeonju_where_go.MainActivity.log_in_off;
import static com.racos.racos2.jeonju_where_go.MainActivity.user_email;

public class lastpage_fromMypage extends AppCompatActivity {    //implements TMapGpsManager.onLocationChangedCallback, TMapView.OnLongClickListenerCallback
    RelativeLayout mapLayout;
    ImageView lastpage_map_zoom;
    ImageView imageView_review;

    static double Latitude_onlymap,Longtitude_onlymap;
    static String balloon_name;

    //카카오
    ImageView imageView_kakaolink;

    //좋아요
    static boolean like_on_off=true;



    //전화걸기 버튼
    Button callbtn;


    ImageView lastpage_imageView_like,imageView11;

    TextView like_textView;


    //아이템 목록
    TextView name,address,like,phone,menu,link,time;
    TextView top_textView1,top_textView2;

    //파이어베이스
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference hiRef = database.getReference();


    boolean check_on = false;

    /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이미지 뷰*/
    ViewFlipper Flipper; //Flipper
    ImageView url1,url2,url3;
    /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ지도*/
    TMapView tMapView;//지도
    TMapPolyLine polyline;//경로
    TMapData tMapData;
    TMapMarkerItem tourMarkerItem; // 마커
    MypageActivity_Item item_mypage;
    Item item;
    Firebase_CheckDB hi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lastpage);



        Button mButton = (Button) findViewById(R.id.MMS);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call Intent to send sms
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "", null));
                link = (TextView)findViewById(R.id.lastpage_link);
                link.setText(item.getLink());
                intent.putExtra("sms_body", item.getLink());
                startActivity(intent);

            }
        });


        hi2 = new Firebase_CheckDB();



        //어뎁터에서 item 정보 얻기.
        Intent intent2=getIntent();
        item_mypage =  (MypageActivity_Item) intent2.getSerializableExtra("text2");
        item = new Item(this,item_mypage.getImage(),item_mypage.getImage2(),item_mypage.getImage3(),item_mypage.getName(),item_mypage.getAddress(),item_mypage.getLike(),item_mypage.getLatitude(),item_mypage.getLongtitude(),item_mypage.getLink(),item_mypage.getMenu(),item_mypage.getPhone(),item_mypage.getTime());


        top_textView1 = (TextView)findViewById(R.id.top_textView1);
        top_textView2 = ( TextView)findViewById(R.id.top_textView2);

        name = (TextView)findViewById(R.id.lastpage_name);
        name.setText(item.getName());

        top_textView1.setText(loc);
        top_textView2.setText(" ▶ "+ info + " ▶ " + item.getName());


        imageView_review = (ImageView)findViewById(R.id.imageView_review);
        imageView_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(log_in_off==false)
                {
                    Toast.makeText(getApplicationContext(), "로그인 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent;
                    intent = new Intent(getApplicationContext(), Activity_Review.class);
                    intent.putExtra("name", item.getName());
                    intent.putExtra("email", user_email);
                    startActivity(intent);
                }
            }
        });



        //글라이드 + Item정보 가져와서 넣기
        url1 = (ImageView)findViewById(R.id.image2);
        url2 = (ImageView)findViewById(R.id.image3);
        url3 = (ImageView)findViewById(R.id.image4);

        Glide.with(this).load(item.getImage()).into(url1);
        Glide.with(this).load(item.getImage2()).into(url2);
        Glide.with(this).load(item.getImage3()).into(url3);


        name = (TextView)findViewById(R.id.lastpage_name);
        name.setText(item.getName());

        address = (TextView)findViewById(R.id.lastpage_address);
        address.setText(item.getAddress());

        phone = (TextView)findViewById(R.id.lastpage_phone);
        phone.setText(item.getPhone());


        time = (TextView)findViewById(R.id.lastpage_time);
        time.setText(item.getTime());


        menu = (TextView)findViewById(R.id.lastpage_menu);
        menu.setText(item.getMenu());

        link = (TextView)findViewById(R.id.lastpage_link);


        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homepage = item.getLink();
                Intent callhome;
                callhome = new Intent(Intent.ACTION_VIEW, Uri.parse(homepage));
                startActivity(callhome);
            }
        });



        imageView11 = (ImageView)findViewById(R.id.imageView11);





        imageView11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!log_in_off)
                {
                    Toast.makeText(getApplicationContext(), "로그인 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (hi2.inner_CheckDB(item.getName()) == 1) {
                        Toast.makeText(getApplicationContext(), "이미 카트에 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {

                        hiRef = database.getReference("유저").child(user_email.substring(0, user_email.length() - 4)).child("cart");
                        hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.
                            int k = 0;   //딱 한번만 실행되게 하기위한 변수.

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                                    if (snapShot.child("count").getValue(String.class) != null && k == 0) {
                                        System.out.println(Icart_count);
                                        System.out.println(Icart_count);
                                        System.out.println(Icart_count);
                                        System.out.println(Icart_count);
                                        System.out.println(Icart_count);
                                        Icart_count += 1;
                                        hiRef.child("TotalCount").child("count").setValue(Integer.toString(MainActivity.Icart_count));
                                        hiRef.child(item.name).child("address").setValue(item.address);
                                        hiRef.child(item.name).child("name").setValue(item.name);
                                        hiRef.child(item.name).child("Latitude").setValue(item.Latitude);
                                        hiRef.child(item.name).child("Longtitude").setValue(item.Longtitude);

                                        hiRef.child(item.name).child("like").setValue(item.like);
                                        hiRef.child(item.name).child("phone").setValue(item.phone);
                                        hiRef.child(item.name).child("link").setValue(item.link);
                                        hiRef.child(item.name).child("time").setValue(item.time);
                                        hiRef.child(item.name).child("menu").setValue(item.menu);
                                        hiRef.child(item.name).child("url1").setValue(item.image);
                                        hiRef.child(item.name).child("url2").setValue(item.image2);
                                        hiRef.child(item.name).child("url3").setValue(item.image3);
                                        Toast.makeText(getApplicationContext(), "카트에 담기 완료", Toast.LENGTH_SHORT).show();
                                        new MypageActivity().My_invalidate();
                                        break;
                                    }


                                }
                                k++;
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("read error");
                            }

                        });


                    }

                }

            }

        });






        lastpage_imageView_like = (ImageView)findViewById(R.id.lastpage_imageView_like);


        like_textView = (TextView)findViewById(R.id.like_textView);

        like_textView.setText(item.getLike());




        //카카오 연동
        imageView_kakaolink = (ImageView)findViewById(R.id.imageView_kakaolink);
        imageView_kakaolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  shareKakao(imageView_kakaolink);
            }
        });






        lastpage_imageView_like = (ImageView)findViewById(R.id.lastpage_imageView_like);

        lastpage_imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ전화걸기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        callbtn = (Button)findViewById(R.id.callbtn);

        final String tel = ("tel:"+item.getPhone());



        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));


            }
        });

/**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡFlipper를 이용한 이미지슬라이드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        Flipper = (ViewFlipper) findViewById(R.id.Flipper);//Flipper 객체

        Animation showln = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Flipper.setInAnimation(showln);
        Animation showout = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        Flipper.setOutAnimation(showout);

        Flipper.setFlipInterval(2500);
        Flipper.startFlipping();
        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/



        mapLayout = (RelativeLayout) findViewById(R.id.mapView);
        tMapView = new TMapView(this); // 지도 객체
        mapLayout.addView(tMapView);
        polyline = new TMapPolyLine();//출 도착 객체
        tMapData = new TMapData();
        tourMarkerItem = new TMapMarkerItem();//마커 객체



        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        tMapView.setSKTMapApiKey(getString(R.string.tmap_api_key));// API
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);//한국어
        tMapView.setIconVisibility(true);//아이콘보이게하는거
        tMapView.setZoomLevel(18);//줌레벨 숫자가 높을수록 확대
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);//지도 종류
        //tMapView.setCompassMode(false);//나침반
        tMapView.setTrackingMode(false);//일정시간 후 화면이 처음위치로 다시 옴

        Latitude_onlymap = item.getLatitude();
        Longtitude_onlymap = item.getLongtitude();




        Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TMapPoint tmappoint = new TMapPoint(Latitude_onlymap, Longtitude_onlymap);
                tourMarkerItem.setTMapPoint(tmappoint);
                tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);


                tMapView.setCenterPoint(Longtitude_onlymap, Latitude_onlymap, true);
                tMapView.addMarkerItem("sss", tourMarkerItem);




                balloon_name = item.getName();
                tourMarkerItem.setCanShowCallout(true);
                Bitmap bitmap = BitmapFactory.decodeResource(lastpage_fromMypage.this.getResources(), R.drawable.maintbar);
                tourMarkerItem.setCalloutTitle(balloon_name);
                tourMarkerItem.setCalloutLeftImage(bitmap);
                tourMarkerItem.setAutoCalloutVisible(true);




            }
        }, 500);


        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
        lastpage_map_zoom = (ImageView) findViewById(R.id.lastpage_map_zoom);
        lastpage_map_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapintent = new Intent(lastpage_fromMypage.this, Onlymap.class);
                mapintent.putExtra("item",item);
                startActivity(mapintent);

            }
        });

        /**ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ지도 찍기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/











        lastpage_imageView_like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                check_on = false;

                if(loc!="모든 지역") {
                    if (log_in_off == true) {


                        //데이터베이스 연동 + 좋아요 연동
                        hiRef = database.getReference(info).child(loc);
                        hiRef.addValueEventListener(new ValueEventListener() {          //addValueEventlister은 루트의 message가 가지고있는 값을 읽어옴+값이 변하면 바로 읽어옴.

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                                    if (item.getName().equals(snapShot.child("name").getValue(String.class)) && check_on==false)
                                    {
                                        check_on = true;
                                        hiRef = snapShot.getRef();

                                        String str = snapShot.child("like_list").child(user_email.substring(0,user_email.length()-4)).getValue(String.class);
                                        String fb_like =  snapShot.child("like").getValue(String.class);
                                        int a = Integer.parseInt(fb_like);
                                        //like_list 댓글단 리스트에 없으면
                                        if(str==null) {

                                            a++;

                                            item.setLike(Integer.toString(a));

                                            Toast.makeText(lastpage_fromMypage.this, "좋아요 +1", Toast.LENGTH_SHORT).show();
                                            hiRef.child("like_list").child(user_email.substring(0, user_email.length() - 4)).setValue("1");
                                            hiRef.child("like").setValue(Integer.toString(a));
                                            like_textView.setText(Integer.toString(a));
                                            return;
                                        }
                                        else           //like_list 댓글단 리스트에 있으면
                                        {

                                            a--;

                                            item.setLike(Integer.toString(a));

                                            Toast.makeText(lastpage_fromMypage.this, "좋아요 취소", Toast.LENGTH_SHORT).show();
                                            hiRef.child("like_list").child(user_email.substring(0, user_email.length() - 4)).setValue(null);
                                            hiRef.child("like").setValue(Integer.toString(a));
                                            like_textView.setText(Integer.toString(a));
                                            return;
                                        }

                                    }


                                }

                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("read error");
                            }
                        });
                        like_on_off = false;

                    }
                    else if (log_in_off == false) {
                        Toast.makeText(getApplicationContext(), "로그인 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }//  if(loc != "모든음식") 지점일때 종단

            }});



        /*    --------------------------  GPS 읽어오기  -------------------------     */
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //권한 얻기
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        try {
            Log.e("gogogogo","testesttestest");
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            else
                Log.e("error","permission error");
            Log.e("gogogogo","testesttestest");
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    100, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        /*    --------------------------  GPS 하단  -------------------------     */





    }//온크리트 종단










    /** ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡFlipper를 이용한 이미지슬라이드ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.btn3:
                Flipper.stopFlipping();
                Flipper.showPrevious();
                Flipper.setFlipInterval(2500); // 1초에
                Flipper.startFlipping(); // 한번씩 Flipping
                break;
            case R.id.btn4:
                Flipper.stopFlipping();
                Flipper.showNext();
                Flipper.setFlipInterval(2500); // 1초에
                Flipper.startFlipping(); // 한번씩 Flipping
                break;
        }
    }


    //
//    @Override
//    public void onLocationChange(Location location) {
//        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
//    }
//
//    @Override
//    public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
//
//    }





//    //카카오 연동 클래스
//    public void shareKakao(View v) {
//        try {
//            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
//
//            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
//
//
//            String url = "https://firebasestorage.googleapis.com/v0/b/myappgong.appspot.com/o/%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%A7%81%ED%81%AC%2Fkakao_link.jpg?alt=media&token=3a10f300-1439-4f02-936e-3f1601d52c6f";
//            kakaoBuilder.addImage(url, 720, 1024);
//
//            kakaoBuilder.addText("    아직도 모르신다구요? \n    전주에 대한 모든 정보가\n    모두~ 있다고요!");
//            kakaoBuilder.addWebLink("관련 홈페이지로 이동","http://www.jeonju.go.kr/");
//            kakaoBuilder.addAppButton("앱 설치하기");
//
//            kakaoLink.sendMessage(kakaoBuilder.build(), this);
//
//
//
//        } catch (KakaoParameterException e) {
//            e.printStackTrace();
//        }
//    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.

            Log.e("GPS Test","GPS:"+longitude+"   "+latitude);
            tMapView.setLocationPoint(longitude,latitude);
            // tMapView.setCenterPoint(longitude,latitude);
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };



}//클래스 종단
