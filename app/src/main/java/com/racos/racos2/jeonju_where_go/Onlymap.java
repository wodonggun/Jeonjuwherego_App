package com.racos.racos2.jeonju_where_go;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.text.DecimalFormat;

import static com.racos.racos2.jeonju_where_go.R.id.mapView;
import static com.racos.racos2.jeonju_where_go.lastpage.balloon_name;
import static com.racos.racos2.jeonju_where_go.MainActivity.global_latitude;
import static com.racos.racos2.jeonju_where_go.MainActivity.global_longtitude;

public class Onlymap extends AppCompatActivity {

    RelativeLayout mapLayout;
    TMapView tMapView;//지도
    TMapPolyLine polyline;//경로
    TMapPoint endpoint; // 도착점
    TMapData tmapdata;
    TMapMarkerItem tourMarkerItem; // 마커
    TMapPoint tpoint, tpoint2;
    TMapGpsManager gps;
    TMapPoint tmappoint;

    //넘겨받은 Item 객체
    Item item;


    double distance2;


    Context mContext = null;
    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlymap);

        btn = (ImageView) findViewById(R.id.btn1);
        mapLayout = (RelativeLayout) findViewById(mapView);
        tMapView = new TMapView(this); // 지도 객체
        polyline = new TMapPolyLine();//출 도착 객체
        tmapdata = new TMapData();
        tourMarkerItem = new TMapMarkerItem();//마커 객체

        item =  (Item)this.getIntent().getSerializableExtra("item");

/** ---------------------------------------지도 기본설정--------------------------------------------*/
        tMapView.setSKTMapApiKey(getString(R.string.tmap_api_key));// API
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);//한국어
        tMapView.setIconVisibility(true);//아이콘보이게하는거
        tMapView.setZoomLevel(16);//줌레벨 숫자가 높을수록 확대
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);//지도 종류
        tMapView.setCompassMode(false);//나침반
        tMapView.setTrackingMode(false);//일정시간 후 화면이 처음위치로 다시 옴
        tMapView.setLocationPoint(global_longtitude,global_latitude);
        //tMapView.setCenterPoint(global_longtitude,global_latitude);

        mapLayout.addView(tMapView);
//-----------------------------------------------내위치 --------------------------------------------//




        //---------------------------------------------장소위치 --------------------------------------------//


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tmappoint = new TMapPoint(item.getLatitude(), item.getLongtitude());
                tourMarkerItem.setTMapPoint(tmappoint);
                tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);



                tMapView.setCenterPoint(item.getLongtitude(), item.getLatitude(), true);
                tMapView.addMarkerItem("sss", tourMarkerItem);


                tourMarkerItem.setCanShowCallout(true);
                Bitmap bitmap = BitmapFactory.decodeResource(Onlymap.this.getResources(), R.drawable.maintbar);
                tourMarkerItem.setCalloutTitle(balloon_name);
                tourMarkerItem.setCalloutLeftImage(bitmap);
                tourMarkerItem.setAutoCalloutVisible(true);

            }
        }, 1000);




    }



    //-----------------------------------------------마커 --------------------------------------------//










    public void onClick1(View v) {
        endpoint = new TMapPoint(global_latitude, global_longtitude);
        tmapdata.findPathData(endpoint, tmappoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {

                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(13);
                tMapView.addTMapPath(tMapPolyLine);

                distance2 = tMapPolyLine.getDistance() / 1000;




            }




        });
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    TextView distancetext = (TextView)findViewById(R.id.distancetext);
                    distancetext = (TextView)findViewById(R.id.distancetext);
                    TextView timeText = (TextView)findViewById(R.id.timetext);
                    timeText = (TextView)findViewById(R.id.timetext);
                    DecimalFormat decimal = new DecimalFormat("#.##");
                    DecimalFormat decimal2 = new DecimalFormat("#");
                    distancetext.setText(decimal.format(distance2) + " km");
                    timeText.setText(decimal2.format(distance2 / 60) + "시간" + decimal2.format(distance2 % 60) + "분");

                } catch (Exception e) {
                    Toast.makeText(mContext, "주소가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }, 3000);


        tMapView.setCenterPoint((tmappoint.getLongitude() + endpoint.getLongitude()) / 2, (tmappoint.getLatitude() + endpoint.getLatitude()) / 2);

        double zoomlat;
        double zoomlon;

        if(endpoint.getLatitude() - tmappoint.getLatitude()>0) {
            zoomlat = endpoint.getLatitude() - tmappoint.getLatitude();
        }else{
            zoomlat = tmappoint.getLatitude() - endpoint.getLatitude();

        }
        if(endpoint.getLongitude() - tmappoint.getLongitude()>0) {
            zoomlon = endpoint.getLongitude() - tmappoint.getLongitude();
        }else{
            zoomlon = tmappoint.getLongitude() - endpoint.getLongitude();
        }


        tMapView.zoomToSpan(zoomlat, zoomlon);



    }



}