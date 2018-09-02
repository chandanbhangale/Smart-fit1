package com.example.chandan.bmicalculatordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView iv1;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        iv1 =(ImageView)findViewById(R.id.iv1);
        animation= AnimationUtils.loadAnimation(this,R.anim.a1);
        iv1.startAnimation(animation);

        //      sp=getSharedPreferences("p1",MODE_PRIVATE);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4800);//sweegy

                    Intent i=new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
