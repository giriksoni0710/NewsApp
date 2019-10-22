package com.crazy4web.recyclerintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager layoutManager;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequestQueue;



    List<String> dataitems = new ArrayList<>();

    private String url;

    List<String> titlelist, urllist;
    LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        titlelist = new ArrayList<>();
        urllist = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.myrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        sendandreceivedata();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("mymessage"));





    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String urldata = intent.getStringExtra("url");



            intent = new Intent(getApplicationContext(), WebActivity.class);
            intent.putExtra("finalurl", urldata);


            startActivity(intent);


//            Log.d("urldata", urldata);



        }
    };

    private void sendandreceivedata() {


        url = "https://www.reddit.com/.json";
        mRequestQueue = Volley.newRequestQueue(this);

        mStringRequestQueue = new StringRequest(Request.Method.GET,url,(response) ->{

            dataitems.add(response.toString());

            Log.d("hello","in");


            for(String items : dataitems){


                Log.d("items",items);

                try {



                    JSONObject first = new JSONObject(items);


                    JSONObject second = first.getJSONObject("data");

                    JSONArray third = second.getJSONArray("children");

                    for (int j = 0; j<24;j++) {
                        JSONObject fourth = third.getJSONObject(j);

                        JSONObject fifth = fourth.getJSONObject("data");

                        String title = fifth.getString("title");


                        String url = fifth.getString("url");



                        titlelist.add(title.toString());

                        urllist.add(url.toString());


                    }


                    Log.d("url", "The url is " + urllist);


                    Log.d("list", ""+titlelist);

                    mAdaptor = new MyAdaptor(this,titlelist, urllist);
                    recyclerView.setAdapter(mAdaptor);











                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



        }, (error)->{

            Log.d("error", ""+error);
        });

        mRequestQueue.add(mStringRequestQueue);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
