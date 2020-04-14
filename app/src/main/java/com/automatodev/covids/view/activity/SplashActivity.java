package com.automatodev.covids.view.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.automatodev.covids.R;
public class SplashActivity extends AppCompatActivity {
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.logo_splash);

        RotateAnimation r = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,
                .5f,RotateAnimation.RELATIVE_TO_SELF,.5f);

        r.setDuration(500);
        logo.startAnimation(r);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        };
        timer.start();
    }
}
