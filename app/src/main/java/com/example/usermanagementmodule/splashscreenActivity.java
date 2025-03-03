package com.example.usermanagementmodule;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.usermanagementmodule.Main.sampledata.MainActivity;

public class splashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashscreen);
        //start the main screen in 2sec
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               startActivity(new Intent(splashscreenActivity.this, MainActivity.class));
               finish();//finish this activty
            }
        },2000);//means 2sec
    }
}