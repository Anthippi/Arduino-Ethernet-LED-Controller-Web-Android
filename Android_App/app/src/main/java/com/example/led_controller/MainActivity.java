package com.example.led_controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "http://arduinoIP"; 
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLedOn = findViewById(R.id.buttonLedOn);
        Button buttonLedOff = findViewById(R.id.buttonLedOff);

        buttonLedOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("/?button1on");
            }
        });

        buttonLedOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("/?button2off");
            }
        });
    }

    private void sendRequest(String endpoint) {
        new HttpRequestTask().execute(SERVER_URL + endpoint);
    }

    private static class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                Log.d(TAG, "Request URL: " + url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }

                    in.close();
                    response = content.toString();
                } else {
                    Log.e(TAG, "Error in connection, Response Code: " + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Exception: ", e);
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Response: " + result);
        }
    }
}
