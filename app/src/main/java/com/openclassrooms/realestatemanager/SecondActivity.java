package com.openclassrooms.realestatemanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
