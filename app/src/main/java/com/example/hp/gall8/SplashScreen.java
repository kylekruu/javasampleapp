package com.example.hp.gall8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.os.SystemClock.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageView.startAnimation(pulse);
        Thread myThread = new Thread(){

        @Override
        public void run() {
            try {

                sleep(3000);
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
        };
            myThread.start();


    }
}
