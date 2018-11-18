package com.example.racos2.jeonju_where_go;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Racos7 on 2017-07-20.
 */

public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Thread.sleep(1500);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
