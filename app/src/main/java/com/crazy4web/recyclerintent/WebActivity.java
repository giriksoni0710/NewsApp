package com.crazy4web.recyclerintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    WebView web;
    String urldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getExtras();

            urldata = bundle.getString("finalurl");

        if(urldata!=null) {
            web = findViewById(R.id.webview);
            web.getSettings().setJavaScriptEnabled(true);

            web.loadUrl(urldata);
        }


    }



}
