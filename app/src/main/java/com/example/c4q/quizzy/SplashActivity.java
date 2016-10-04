package com.example.c4q.quizzy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hyun on 10/3/16.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                start();

            }
        }, 3000);

//        Intent intent2 = new Intent (getApplicationContext(), MainActivity.class);
//        Intent intent3 = new Intent (SplashActivity.this, MainActivity.class);
//        Intent intent4 = new Intent (getContext(), MainActivity.class);

    }

    private void start() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    private Context getContext(){
        return null;
    }
}
