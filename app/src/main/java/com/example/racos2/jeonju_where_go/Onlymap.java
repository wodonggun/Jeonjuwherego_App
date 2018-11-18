package com.example.racos2.jeonju_where_go;

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

import static com.example.racos2.jeonju_where_go.R.id.mapView;
import static com.example.racos2.jeonju_where_go.lastpage.Latitude_onlymap;
import static com.example.racos2.jeonju_where_go.lastpage.Longtitude_onlymap;
import static com.example.racos2.jeonju_where_go.lastpage.balloon_name;

public class Onlymap extends AppCompatActivity {//TMapView.OnLongClickListenerCallback, iplements TMapGpsManager.onLocationChangedCallback

    private String apiKey = "1c9faf4b-9050-31c3-8c7f-7089192b6e67";
    RelativeLayout mapLayout;
    TMapView tMapView;//지도
    TMapPolyLine polyline;//경로
    TMapPoint startpoint; // 시작점
    TMapPoint endpoint; // 도착점
    TMapData tmapdata;
    TMapMarkerItem tourMarkerItem; // 마커
    TMapPoint tpoint, tpoint2;
    TMapGpsManager gps;
    TMapPoint tmappoint;




    double distance2;


    Context mContext = null;
    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlymap);

        btn = (ImageView) findViewById(R.id.btn1);
        mContext = this;
        mapLayout = (RelativeLayout) findViewById(mapView);
        tMapView = new TMapView(this); // 지도 객체
        polyline = new TMapPolyLine();//출 도착 객체
        tmapdata = new TMapData();
        tourMarkerItem = new TMapMarkerItem();//마커 객체
        mapLayout.addView(tMapView);


/** ---------------------------------------지도 기본설정--------------------------------------------*/
        tMapView.setSKTMapApiKey(apiKey);// API
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);//한국어
        tMapView.setIconVisibility(true);//아이콘보이게하는거
        tMapView.setZoomLevel(18);//줌레벨 숫자가 높을수록 확대
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);//지도 종류
        tMapView.setCompassMode(false);//나침반
        tMapView.setTrackingMode(true);//일정시간 후 화면이 처음위치로 다시 옴
        //tMapView.setTrackingMode(true); // 트래킹모드 사용여부

//-----------------------------------------------내위치 --------------------------------------------//

        gps = new TMapGpsManager(this);
        gps.setMinDistance(5);
        gps.setMinTime(1000);
        gps.setProvider(gps.NETWORK_PROVIDER);
        gps.OpenGps();



        //---------------------------------------------장소위치 --------------------------------------------//


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tmappoint = new TMapPoint(Latitude_onlymap, Longtitude_onlymap);
                tourMarkerItem.setTMapPoint(tmappoint);
                tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);



                tMapView.setCenterPoint(Longtitude_onlymap, Latitude_onlymap, true);
                tMapView.addMarkerItem("sss", tourMarkerItem);


                tourMarkerItem.setCanShowCallout(true);
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.maintbar);
                tourMarkerItem.setCalloutTitle(balloon_name);
                tourMarkerItem.setCalloutLeftImage(bitmap);
                tourMarkerItem.setAutoCalloutVisible(true);

            }
        }, 1000);
    }



    //-----------------------------------------------마커 --------------------------------------------//










    public void onClick1(View v) {
        endpoint = new TMapPoint(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
        tmapdata.findPathData(endpoint, tmappoint, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine tMapPolyLine) {

                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(8);
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
                    tMapView.setLocationPoint((tmappoint.getLongitude() + endpoint.getLongitude()) / 2, (tmappoint.getLatitude() + endpoint.getLatitude()) / 2);
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


//    @Override
//    public void onLocationChange(Location location) {
//        double lat = location.getLatitude(); // 위도
//        double lon = location.getLongitude();// 경도
//        tMapView.setLocationPoint(lon, lat); // 위도, 경도
//    }

//    @Override
//    public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
//
//            tourMarkerItem.setTMapPoint(tMapPoint); //마커를 찾아와서
//            arrayList.add(tourMarkerItem); // 표시해준다
//
//            endpoint = tMapPoint;
//
//            tMapView.addMarkerItem("ss", arrayList.get(0));
//
//    }
}