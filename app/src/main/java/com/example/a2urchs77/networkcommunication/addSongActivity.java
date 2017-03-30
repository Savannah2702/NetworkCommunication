package com.example.a2urchs77.networkcommunication;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class addSongActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        Button addSongButton = (Button) findViewById(R.id.addButton);
        addSongButton.setOnClickListener(this);
    }


    class AddSongAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection) url.openConnection();

                String postData = "songTitle="+params[0] + "&artistName="+params[1]
                + "&yearSong="+params[2];

                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(postData.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(postData.getBytes());
                if (conn.getResponseCode() == 200) {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String all = "", line;
                    while ((line = br.readLine()) != null)
                        all += line;
                    return all;
                } else
                    return "HTTP ERROR:" + conn.getResponseCode();


            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
        }

        public void onPostExecute(String result) {

            new AlertDialog.Builder(addSongActivity.this).
                    setMessage("Server sent back: " + result).
                    setPositiveButton("OK", null).show();
        }
    }

    @Override
    public void onClick(View view) {
        EditText song = (EditText) findViewById(R.id.song);
        String songTitle = song.getText().toString();

        EditText artist = (EditText) findViewById(R.id.artist);
        String artistName = artist.getText().toString();

        EditText year = (EditText) findViewById(R.id.year);
        String yearSong = year.getText().toString();


        new AddSongAsyncTask().execute(songTitle, artistName, yearSong);

            }
}



