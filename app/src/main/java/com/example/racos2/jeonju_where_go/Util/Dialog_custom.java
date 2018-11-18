package com.example.racos2.jeonju_where_go.Util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.racos2.jeonju_where_go.R;


/**
 * Created by Administrator on 2016-11-22.
 */
public class Dialog_custom extends Dialog {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    private ImageView mIcon;
    private TextView mMsg;
    private TextView mBtn1, mBtn2;
    private ImageView mBtnMargin;

    private Drawable mIcon_bg = null;
    private String mType;
    private String msg1, btn1, btn2;

    private BroadcastReceiver receiver_am;


    public String put_data;

    private String result;


    Context mContext;

    //only for matching_adapter


    //버튼두개일때
    public Dialog_custom(Context context, Drawable icon, String msg, String btn_1, String btn_2, String type) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mType = type;
        this.msg1 = msg;
        this.btn1 = btn_1;
        this.btn2 = btn_2;
        this.mIcon_bg = icon;
    }


    //버튼하나일때
    public Dialog_custom(Context context, Drawable icon, String msg, String btn_1, String type) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.mType = type;
        this.msg1 = msg;
        this.btn1 = btn_1;
        this.btn2 = null;
        this.mIcon_bg = icon;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);

        mIcon = (ImageView) findViewById(R.id.icon);
        mMsg = (TextView) findViewById(R.id.msg);
        mBtn1 = (TextView) findViewById(R.id.btn_1);

        mBtnMargin = (ImageView) findViewById(R.id.two_btn_margin);
        mBtn2 = (TextView) findViewById(R.id.btn_2);

        pref = mContext.getSharedPreferences("login", 0);
        editor = pref.edit();

//        버튼 하나일때
        if (btn2 == null) {
            mMsg.setText(msg1);
            mBtn1.setText(btn1);
            mBtn1.setOnClickListener(btnlistener);
            mIcon.setBackground(mIcon_bg);
        }
        //버튼두개일때
        else {
            mMsg.setText(msg1);
            mBtn1.setText(btn1);
            mBtnMargin.setVisibility(View.VISIBLE);
            mBtn2.setVisibility(View.VISIBLE);
            mBtn2.setText(btn2);
            mBtn1.setOnClickListener(btnlistener2);
            mBtn2.setOnClickListener(btnlistener3);
            mIcon.setBackground(mIcon_bg);
        }


    }

    View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {
                //버튼하나일때 아무 작업도 없이 다이얼로그 죽이는 경우
                case "dismiss":
                    Dialog_custom.this.dismiss();
                    break;
            }
        }
    };

    // 왼쪽 버튼
    View.OnClickListener btnlistener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {

            }
        }
    };
    // 오른쪽 버튼
    View.OnClickListener btnlistener3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (mType) {

            }
        }
    };


}

