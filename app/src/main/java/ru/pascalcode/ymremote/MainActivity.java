package ru.pascalcode.ymremote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button playPause;

    private Button previous;

    private Button next;

    private Button dislike;

    private Button like;

    private ImageButton settingsButton;

    private Button screenSaver;

    private Button radio;

    private Button favorite;

    private Button volumeUp;

    private Button volumeDown;

    private final static String URL_FORMAT = "http://%s:%s/%s/%s";

    private final static String YM_REQUEST_MAPPING = "yandexmusic";

    private final static String ENV_REQUEST_MAPPING = "environment";

    private final static String PORT = "1620";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIpFromSharedPreferences().isEmpty()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        playPause = findViewById(R.id.playPause);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        dislike = findViewById(R.id.dislike);
        like = findViewById(R.id.like);
        settingsButton = findViewById(R.id.settingsButton);
        screenSaver = findViewById(R.id.screenSaver);
        radio = findViewById(R.id.radio);
        favorite = findViewById(R.id.favorite);
        volumeUp = findViewById(R.id.volumeUp);
        volumeDown = findViewById(R.id.volumeDown);
        playPause.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.PLAYPAUSE);
        });
        previous.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.PREVIOUS);
        });
        next.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.NEXT);
        });
        dislike.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.DISLIKE);
        });
        like.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.LIKE);
        });
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
        screenSaver.setOnClickListener(view -> {
            new CommandSender().execute(ENV_REQUEST_MAPPING, Command.SCREEN_SAVER);
        });
        radio.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.RADIO);
        });
        favorite.setOnClickListener(view -> {
            new CommandSender().execute(YM_REQUEST_MAPPING, Command.FAVORITE);
        });
        volumeUp.setOnClickListener(view -> {
            new CommandSender().execute(ENV_REQUEST_MAPPING, Command.VOLUME_UP);
        });
        volumeDown.setOnClickListener(view -> {
            new CommandSender().execute(ENV_REQUEST_MAPPING, Command.VOLUME_DOWN);
        });
    }

    private String getIpFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(UserSettings.IP_ADDRESS, "");
    }

    private class CommandSender extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String request_mapping = strings[0];
                String command = strings[1];
                URL url = new URL(String.format(URL_FORMAT, getIpFromSharedPreferences(), PORT, request_mapping, command));
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}