package com.example.racos2.jeonju_where_go;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static com.example.racos2.jeonju_where_go.MainActivity.loc;
import static com.example.racos2.jeonju_where_go.R.id.mapView;

/**
 * Created by Racos1 on 2017-11-20.
 */

public class RecomandActivity extends AppCompatActivity {
    private String apiKey = "1c9faf4b-9050-31c3-8c7f-7089192b6e67";
    RelativeLayout mapLayout;
    TMapView tmapView;//지도
    TMapPolyLine polyline;//경로
    TMapPoint startpoint; // 시작점
    TMapPoint endpoint; // 도착점
    TMapData tmapdata;
    TMapMarkerItem tourMarkerItem; // 마커
    TMapMarkerItem tourMarkerItem2; // 마커
    TMapMarkerItem tourMarkerItem3; // 마커
    TMapMarkerItem tourMarkerItem4; // 마커
    TMapMarkerItem tourMarkerItem5; // 마커
    TMapPoint pass1, pass2, pass3;
    Context mContext;
    Bitmap bitmap;
    private ArrayList<TMapPoint> passList = new ArrayList<TMapPoint>();
    private ArrayList<TMapPoint> linePassList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomand);

        mContext = this;
        mapLayout = (RelativeLayout) findViewById(mapView);
        tmapView = new TMapView(this); // 지도 객체
        polyline = new TMapPolyLine();//출 도착 객체
        tmapdata = new TMapData();
        tourMarkerItem = new TMapMarkerItem();//마커 객체
        tourMarkerItem2 = new TMapMarkerItem();//마커 객체
        tourMarkerItem3 = new TMapMarkerItem();//마커 객체
        tourMarkerItem4 = new TMapMarkerItem();//마커 객체
        tourMarkerItem5 = new TMapMarkerItem();//마커 객체


        mapLayout = (RelativeLayout) findViewById(R.id.Recomap);
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.iconcamera);

        mapLayout.addView(tmapView);


/** ---------------------------------------지도 기본설정--------------------------------------------*/


        tmapView.setSKTMapApiKey(apiKey);// API
        tmapView.setLanguage(TMapView.LANGUAGE_KOREAN);//한국어
        tmapView.setIconVisibility(true);//아이콘보이게하는거

        tmapView.setMapType(TMapView.MAPTYPE_STANDARD);//지도 종류
        tmapView.setCompassMode(false);//나침반
        tmapView.setTrackingMode(true);//일정시간 후 화면이 처음위치로 다시 옴
        //tMapView.setTrackingMode(true); // 트래킹모드 사용여부
        if(loc == "roco1")
            tmapView.setZoomLevel(13);//줌레벨 숫자가 높을수록 확대
        else
            tmapView.setZoomLevel(15);//줌레벨 숫자가 높을수록 확대
        TMapData.TMapPathType tMapPathType;
        tMapPathType = TMapData.TMapPathType.PEDESTRIAN_PATH;




        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.gongback);
        tmapView.setTMapPathIcon(bitmap, bitmap, bitmap);




        if (loc == "roco4") {
            startpoint = new TMapPoint(35.813306, 127.14925100000005); //전동성당
            endpoint = new TMapPoint(35.8143606, 127.15460089999999); // 한옥마을 오목대
            pass1 = new TMapPoint(35.8149838, 127.14996070000007); // 경기전
            pass2 = new TMapPoint(35.8157006, 127.15130180000006); //부채 문화관
            pass3 = new TMapPoint(35.8154438, 127.15195979999999); // 전주 명품관
            linePassList.add(pass1);
            linePassList.add(pass2);
            linePassList.add(pass3);


            tmapdata.findPathDataWithType(tMapPathType, startpoint, endpoint, linePassList, 0, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                    tmapView.setCenterPoint(127.15106070000007, 35.8149838);
                    tmapView.setLocationPoint(127.15106070000007, 35.8149838);

                    tourMarkerItem.setTMapPoint(startpoint);
                    tourMarkerItem.setName("전동성당");
                    tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.start);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem.setIcon(bitmap);
                    tourMarkerItem.setCalloutTitle("전동성당");
                    tmapView.addMarkerItem("전동성당", tourMarkerItem);
                    tourMarkerItem2.setTMapPoint(endpoint);
                    tourMarkerItem2.setName("전주 명품관");
                    tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.end);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem2.setIcon(bitmap);
                    tourMarkerItem2.setCalloutTitle("전주명품관");
                    tmapView.addMarkerItem("전주 명품관", tourMarkerItem2);
                    tourMarkerItem3.setTMapPoint(pass1);
                    tourMarkerItem3.setName("경기전");
                    tourMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem3.setIcon(bitmap);
                    tourMarkerItem3.setCalloutTitle("경기전");
                    tmapView.addMarkerItem("경기전", tourMarkerItem3);
                    tourMarkerItem4.setTMapPoint(pass2);
                    tourMarkerItem4.setName("오목대");
                    tourMarkerItem4.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem4.setIcon(bitmap);
                    tourMarkerItem4.setCalloutTitle("오목대");
                    tmapView.addMarkerItem("오목대", tourMarkerItem4);
                    tourMarkerItem5.setTMapPoint(pass3);
                    tourMarkerItem5.setName("부채문화관");
                    tourMarkerItem5.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem5.setIcon(bitmap);
                    tourMarkerItem5.setCalloutTitle("부채문화관");
                    tmapView.addMarkerItem("부채문화관", tourMarkerItem5);

                    tourMarkerItem.setAutoCalloutVisible(true);
                    tourMarkerItem.setCanShowCallout(true);
                    tourMarkerItem2.setAutoCalloutVisible(true);
                    tourMarkerItem2.setCanShowCallout(true);
                    tourMarkerItem3.setAutoCalloutVisible(true);
                    tourMarkerItem3.setCanShowCallout(true);
                    tourMarkerItem4.setAutoCalloutVisible(true);
                    tourMarkerItem4.setCanShowCallout(true);
                    tourMarkerItem5.setAutoCalloutVisible(true);
                    tourMarkerItem5.setCanShowCallout(true);

                    tMapPolyLine.setLineWidth(12);
                    tMapPolyLine.setLineColor(Color.BLUE);

                    tmapView.addTMapPath(tMapPolyLine);

                }
            });
        }

        if (loc == "roco2") {
            startpoint = new TMapPoint(35.8151458, 127.15393489999997); // 전주 한옥마을
            endpoint = new TMapPoint(35.8151458, 127.153935); //한옥마을
            pass1 = new TMapPoint(35.8157777, 127.14996070000007); //경기전
            pass2 = new TMapPoint(35.8127226, 127.15722210000001); // 전주 향교
            pass3 = new TMapPoint(35.8175443, 127.15239659999997); // 선각사
            linePassList.add(pass1);
            linePassList.add(pass2);
            linePassList.add(pass3);


            tmapdata.findPathDataWithType(tMapPathType, startpoint, endpoint, linePassList, 0, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                    tmapView.setCenterPoint(127.15393489999997, 35.8151458);
                    tmapView.setLocationPoint(127.15393489999997, 35.8151458);

                    tourMarkerItem.setTMapPoint(startpoint);
                    tourMarkerItem.setName("전주한옥마을");
                    tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.start);

                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem.setIcon(bitmap);
                    tourMarkerItem.setCalloutTitle("전주한옥마을");
                    tmapView.addMarkerItem("전주한옥마을", tourMarkerItem);

                    tourMarkerItem3.setTMapPoint(pass1);
                    tourMarkerItem3.setName("경기전");
                    tourMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.end);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);

                    tourMarkerItem3.setIcon(bitmap);
                    tourMarkerItem3.setCalloutTitle("경기전");
                    tmapView.addMarkerItem("경기전", tourMarkerItem3);
                    tourMarkerItem4.setTMapPoint(pass2);
                    tourMarkerItem4.setName("전주 향교");
                    tourMarkerItem4.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);

                    tourMarkerItem4.setIcon(bitmap);
                    tourMarkerItem4.setCalloutTitle("전주 향교");
                    tmapView.addMarkerItem("전주 향교", tourMarkerItem4);
                    tourMarkerItem5.setTMapPoint(pass3);
                    tourMarkerItem5.setName("선각사");
                    tourMarkerItem5.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);

                    tourMarkerItem5.setIcon(bitmap);
                    tourMarkerItem5.setCalloutTitle("선각사");
                    tmapView.addMarkerItem("선각사", tourMarkerItem5);
                    tourMarkerItem.setAutoCalloutVisible(true);
                    tourMarkerItem.setCanShowCallout(true);
                    tourMarkerItem2.setAutoCalloutVisible(true);
                    tourMarkerItem2.setCanShowCallout(true);
                    tourMarkerItem3.setAutoCalloutVisible(true);
                    tourMarkerItem3.setCanShowCallout(true);
                    tourMarkerItem4.setAutoCalloutVisible(true);
                    tourMarkerItem4.setCanShowCallout(true);
                    tourMarkerItem5.setAutoCalloutVisible(true);
                    tourMarkerItem5.setCanShowCallout(true);

                    tMapPolyLine.setLineWidth(12);
                    tMapPolyLine.setLineColor(Color.BLUE);

                    tmapView.addTMapPath(tMapPolyLine);

                }
            });

        }
        if (loc == "roco3") {
            startpoint = new TMapPoint(35.8127226, 127.15722210000001); //전주향교
            endpoint = new TMapPoint(35.80708449999999, 127.16627700000004); //치명자산성지
            pass1 = new TMapPoint(35.8120443, 127.15839030000006); //한벽극장
            pass2 = new TMapPoint(35.8118781, 127.16086280000002); // 오목대
            pass3 = new TMapPoint(35.8157006, 127.15130180000006); // 전주 부채 문화관
            linePassList.add(pass1);
            linePassList.add(pass2);
            linePassList.add(pass3);


            tmapdata.findPathDataWithType(tMapPathType, startpoint, endpoint, linePassList, 0, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                    tmapView.setCenterPoint(127.15739030000006, 35.8120443);
                    tmapView.setLocationPoint(127.15739030000006, 35.8120443);

                    tourMarkerItem.setTMapPoint(startpoint);
                    tourMarkerItem.setName("전주향교");
                    tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.start);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem.setIcon(bitmap);
                    tourMarkerItem.setCalloutTitle("전주향교");
                    tmapView.addMarkerItem("전주향교", tourMarkerItem);
                    tourMarkerItem2.setTMapPoint(endpoint);
                    tourMarkerItem2.setName("치명자산성지");
                    tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.end);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem2.setIcon(bitmap);
                    tourMarkerItem2.setCalloutTitle("치명자산성지");
                    tmapView.addMarkerItem("치명자산성지", tourMarkerItem2);
                    tourMarkerItem3.setTMapPoint(pass1);
                    tourMarkerItem3.setName("한벽극장");
                    tourMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem3.setIcon(bitmap);
                    tourMarkerItem3.setCalloutTitle("한벽극장");
                    tmapView.addMarkerItem("한벽극장", tourMarkerItem3);
                    tourMarkerItem4.setTMapPoint(pass2);
                    tourMarkerItem4.setName("오목대");
                    tourMarkerItem4.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem4.setIcon(bitmap);
                    tourMarkerItem4.setCalloutTitle("오목대");
                    tmapView.addMarkerItem("오목대", tourMarkerItem4);
                    tourMarkerItem5.setTMapPoint(pass3);
                    tourMarkerItem5.setName(" 전주 부채 문화관");
                    tourMarkerItem5.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem5.setIcon(bitmap);
                    tourMarkerItem5.setCalloutTitle(" 전주 부채 문화관");
                    tmapView.addMarkerItem(" 전주 부채 문화관", tourMarkerItem5);

                    tourMarkerItem.setAutoCalloutVisible(true);
                    tourMarkerItem.setCanShowCallout(true);
                    tourMarkerItem2.setAutoCalloutVisible(true);
                    tourMarkerItem2.setCanShowCallout(true);
                    tourMarkerItem3.setAutoCalloutVisible(true);
                    tourMarkerItem3.setCanShowCallout(true);
                    tourMarkerItem4.setAutoCalloutVisible(true);
                    tourMarkerItem4.setCanShowCallout(true);
                    tourMarkerItem5.setAutoCalloutVisible(true);
                    tourMarkerItem5.setCanShowCallout(true);
                    tMapPolyLine.setLineWidth(12);
                    tMapPolyLine.setLineColor(Color.BLUE);

                    tmapView.addTMapPath(tMapPolyLine);

                }
            });

        }
        if (loc == "roco1") {

            tmapView.MapZoomOut();
            tmapView.MapZoomOut();
            tmapView.MapZoomOut();

            startpoint = new TMapPoint(35.8650211, 127.11366939999994);
            endpoint = new TMapPoint(35.8176573, 127.15334830000006); //한옥생활체험관
            pass1 = new TMapPoint(35.8475732, 127.121895);//덕진공원
            pass2 = new TMapPoint(35.8143604, 127.15612899999996); //오목대
            pass3 = new TMapPoint(35.8122152, 127.15721180000003); // 전주향교
            linePassList.add(pass1);
            linePassList.add(pass2);
            linePassList.add(pass3);


            tmapdata.findPathDataWithType(tMapPathType, startpoint, endpoint, linePassList, 0, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                    tmapView.setCenterPoint(127.12815150000006, 35.8433996);
                    tmapView.setLocationPoint(127.12815150000006, 35.8433996);

                    tourMarkerItem.setTMapPoint(startpoint);
                    tourMarkerItem.setName("전동성당");
                    tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.start);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem.setIcon(bitmap);
                    tourMarkerItem.setCalloutTitle("부채문화관");
                    tmapView.addMarkerItem("전동성당", tourMarkerItem);
                    tourMarkerItem2.setTMapPoint(endpoint);
                    tourMarkerItem2.setName("한옥생활체험관");
                    tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.end);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem2.setIcon(bitmap);
                    tourMarkerItem2.setCalloutTitle("한옥생활체험관");
                    tmapView.addMarkerItem("한옥생활체험관", tourMarkerItem2);
                    tourMarkerItem3.setTMapPoint(pass1);
                    tourMarkerItem3.setName("덕진공원");
                    tourMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem3.setIcon(bitmap);
                    tourMarkerItem3.setCalloutTitle("덕진공원");
                    tmapView.addMarkerItem("덕진공원", tourMarkerItem3);
                    tourMarkerItem4.setTMapPoint(pass2);
                    tourMarkerItem4.setName("오목대");
                    tourMarkerItem4.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem4.setIcon(bitmap);
                    tourMarkerItem4.setCalloutTitle("오목대");
                    tmapView.addMarkerItem("오목대", tourMarkerItem4);
                    tourMarkerItem5.setTMapPoint(pass3);
                    tourMarkerItem5.setName(" 전주향교");
                    tourMarkerItem5.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem5.setIcon(bitmap);
                    tourMarkerItem5.setCalloutTitle(" 전주향교");
                    tmapView.addMarkerItem(" 전주향교", tourMarkerItem5);

                    tourMarkerItem.setAutoCalloutVisible(true);
                    tourMarkerItem.setCanShowCallout(true);
                    tourMarkerItem2.setAutoCalloutVisible(true);
                    tourMarkerItem2.setCanShowCallout(true);
                    tourMarkerItem3.setAutoCalloutVisible(true);
                    tourMarkerItem3.setCanShowCallout(true);
                    tourMarkerItem4.setAutoCalloutVisible(true);
                    tourMarkerItem4.setCanShowCallout(true);
                    tourMarkerItem5.setAutoCalloutVisible(true);
                    tourMarkerItem5.setCanShowCallout(true);



                    tMapPolyLine.setLineWidth(12);
                    tMapPolyLine.setLineColor(Color.BLUE);

                    tmapView.addTMapPath(tMapPolyLine);

                }
            });

        }
        if (loc == "roco5") {
            startpoint = new TMapPoint(35.8212559,127.14452210000002); // 한지문화축제
            endpoint = new TMapPoint(35.8153849,127.15421900000001); // 키덜트 팩토리
            pass1 = new TMapPoint(35.8149838, 127.14996070000007); //경기전
            pass3 = new TMapPoint(35.8151064, 127.1539636); //한옥마을
            pass2 = new TMapPoint(35.8154438, 127.15195979999999); // 부채문화관
            linePassList.add(pass1);
            linePassList.add(pass2);
            linePassList.add(pass3);


            tmapdata.findPathDataWithType(tMapPathType, startpoint, endpoint, linePassList, 0, new TMapData.FindPathDataListenerCallback() {
                @Override
                public void onFindPathData(TMapPolyLine tMapPolyLine) {
                    tmapView.setCenterPoint(127.14996070000007, 35.8149838);
                    tmapView.setLocationPoint(127.14996070000007, 35.8149838);

                    tourMarkerItem.setTMapPoint(startpoint);
                    tourMarkerItem.setName("전주 문화의거리");
                    tourMarkerItem.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.start);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem.setIcon(bitmap);
                    tourMarkerItem.setCalloutTitle("전주 문화의거리");
                    tmapView.addMarkerItem("전주 문화의거리", tourMarkerItem);
                    tourMarkerItem2.setTMapPoint(endpoint);
                    tourMarkerItem2.setName("키덜트 팩토리");
                    tourMarkerItem2.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.end);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem2.setIcon(bitmap);
                    tourMarkerItem2.setCalloutTitle("키덜트 팩토리");
                    tmapView.addMarkerItem("키덜트 팩토리", tourMarkerItem2);
                    tourMarkerItem3.setTMapPoint(pass1);
                    tourMarkerItem3.setName("경기전");
                    tourMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem3.setIcon(bitmap);
                    tourMarkerItem3.setCalloutTitle("경기전");
                    tmapView.addMarkerItem("경기전", tourMarkerItem3);
                    tourMarkerItem4.setTMapPoint(pass2);
                    tourMarkerItem4.setName("한옥마을");
                    tourMarkerItem4.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem4.setIcon(bitmap);
                    tourMarkerItem4.setCalloutTitle("한옥마을");
                    tmapView.addMarkerItem("한옥마을", tourMarkerItem4);
                    tourMarkerItem5.setTMapPoint(pass3);
                    tourMarkerItem5.setName("부채문화관");
                    tourMarkerItem5.setVisible(TMapMarkerItem.VISIBLE);
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pass);
                    bitmap = bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    tourMarkerItem5.setIcon(bitmap);
                    tourMarkerItem5.setCalloutTitle("부채문화관");
                    tmapView.addMarkerItem("부채문화관", tourMarkerItem5);
                    tourMarkerItem.setAutoCalloutVisible(true);
                    tourMarkerItem.setCanShowCallout(true);
                    tourMarkerItem2.setAutoCalloutVisible(true);
                    tourMarkerItem2.setCanShowCallout(true);
                    tourMarkerItem3.setAutoCalloutVisible(true);
                    tourMarkerItem3.setCanShowCallout(true);
                    tourMarkerItem4.setAutoCalloutVisible(true);
                    tourMarkerItem4.setCanShowCallout(true);
                    tourMarkerItem5.setAutoCalloutVisible(true);
                    tourMarkerItem5.setCanShowCallout(true);

                    tMapPolyLine.setLineWidth(12);
                    tMapPolyLine.setLineColor(Color.BLUE);

                    tmapView.addTMapPath(tMapPolyLine);

                }
            });

        }
    }
}
