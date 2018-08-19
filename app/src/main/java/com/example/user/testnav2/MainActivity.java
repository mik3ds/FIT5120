package com.example.user.testnav2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        configureNavButton1();
        configureNavButtton2();
        configureWeatherDisplay();
        configureUserName();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void configureNavButton1() {
        Button navbutton1 = (Button) findViewById(R.id.navbutton1);
        navbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main1Activity.class));
            }
        });
    }

    private void configureNavButtton2() {
        Button navbutton2 = (Button) findViewById(R.id.navbutton2);
        navbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

    private void configureUserName() {
        TextView welcome = (TextView) findViewById(R.id.welcometext);
        String displayName = mPreferences.getString(getString(R.string.username), "Guest");
        String temp = "Welcome, " + displayName + "!";
        welcome.setText(temp);
    }

    private void configureWeatherDisplay() {
        TextView weatherdisplay = (TextView) findViewById(R.id.homeWeatherText);

        weatherGetter wg = new weatherGetter();
        String weather = wg.doInBackground();
        JSONObject obj = null;
        try {
            obj = new JSONObject(weather);
            Log.d("FIT5120", obj.toString());
            obj = obj.getJSONObject("main");
            String temp = obj.getString("temp");
            String temp2 = "It is currently " + temp + " degrees in Melbourne";
            weatherdisplay.setText(temp2);
        } catch (Throwable t) {
            Log.e("FIT5120", "Could not load JSON");
        }



    }



}
