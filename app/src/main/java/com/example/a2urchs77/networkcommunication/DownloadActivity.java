package com.example.a2urchs77.networkcommunication;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DownloadActivity extends Activity implements View.OnClickListener {

    class DownloadSong extends AsyncTask<String, Void, String> {
        private static final int HTTP_OK = 200;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String artist1 = params[1];

            HttpURLConnection connection;

            try {
                URL urlObject = new URL(url + "?artist=" + artist1 + "&format=json");
                connection = (HttpURLConnection) urlObject.openConnection();

                if (connection.getResponseCode() == HTTP_OK) {
                    InputStream in = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String lines = "";
                    String line = br.readLine();
                    while (line != null) {
                        lines += line;
                        line = br.readLine();
                    }

                    JSONArray jsonArr = new JSONArray(lines);
                    String curSong, curArtist, curYear;

                    TextView tv = (TextView) findViewById(R.id.tv1);
                    String songs = "";

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject curObj = jsonArr.getJSONObject(i);
                        String name = curObj.getString("song"),
                                artist = curObj.getString("artist"),
                                year = curObj.getString("year");
                        songs += " Name=" + name + "Artist =" + artist + "Year=" + year + "\n";
                    }
                    return songs;
                } else {
                    return "HTTP Error!";
                }
            } catch (IOException e) {
                return "Error!:" + e.getMessage();
            } catch (JSONException e) {
                return "Error: Something went wrong";
            }
        }

        @Override
        protected void onPostExecute (String songs)
        {
            TextView songsTextView = (TextView) findViewById(R.id.tv1);
            songsTextView.setText(songs);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Button go = (Button) findViewById(R.id.btn1);
        go.setOnClickListener(this);
    }

    public void onClick(View v) {
        EditText artist1 = (EditText) findViewById(R.id.et1);
        String artist = (artist1.getText().toString());
        EditText url1 = (EditText) findViewById(R.id.url);
        String url = (url1.getText().toString());

        DownloadSong t = new DownloadSong();
        t.execute(url, artist);
    }
}



