package com.example.racos2.jeonju_where_go;

import android.app.Activity;
import android.widget.Toast;

import static com.example.racos2.jeonju_where_go.MainActivity.log_in_off;
import static com.example.racos2.jeonju_where_go.MainActivity.user_email;

/**
 *
 * 뒤로가기 버튼 핸들러 함수

 */



public class BackPressCloserHandler {


    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloserHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            log_in_off=false;
            user_email ="로그인 해주세요";
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                " 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}
