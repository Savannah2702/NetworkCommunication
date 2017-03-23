package com.example.a2urchs77.networkcommunication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addSongActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        Button addSongButton = (Button) findViewById(R.id.addButton);
        addSongButton.setOnClickListener(this);
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

    class AddSongAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String postData = "song=" + params[0] "&artistName=" + params[1] "&yearSong=" + params[2];
            {

            }
    }
}
