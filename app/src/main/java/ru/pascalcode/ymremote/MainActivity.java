package ru.pascalcode.ymremote;

import static ru.pascalcode.ymremote.Constant.ENV_REQUEST_MAPPING;
import static ru.pascalcode.ymremote.Constant.PORT;
import static ru.pascalcode.ymremote.Constant.URL_FORMAT;
import static ru.pascalcode.ymremote.Constant.YM_REQUEST_MAPPING;

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
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageButton settingsButton;

    private Button playPause;

    private Button previous;

    private Button next;

    private Button dislike;

    private Button like;

    private Button radio;

    private Button favorite;

    private Button volumeUp;

    private Button volumeDown;

    private Button screenSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIpFromSharedPreferences().isEmpty()) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        settingsButton = findViewById(R.id.settingsButton);

        playPause = findViewById(R.id.playPause);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        dislike = findViewById(R.id.dislike);
        like = findViewById(R.id.like);
        radio = findViewById(R.id.radio);
        favorite = findViewById(R.id.favorite);

        volumeUp = findViewById(R.id.volumeUp);
        volumeDown = findViewById(R.id.volumeDown);
        screenSaver = findViewById(R.id.screenSaver);

        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        playPause.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.PLAYPAUSE));
        previous.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.PREVIOUS));
        next.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.NEXT));
        dislike.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.DISLIKE));
        like.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.LIKE));
        radio.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.RADIO));
        favorite.setOnClickListener(view -> new CommandSender().execute(YM_REQUEST_MAPPING, YMCommand.FAVORITE));

        volumeUp.setOnClickListener(view -> new CommandSender().execute(ENV_REQUEST_MAPPING, EnvCommand.VOLUME_UP));
        volumeDown.setOnClickListener(view -> new CommandSender().execute(ENV_REQUEST_MAPPING, EnvCommand.VOLUME_DOWN));
        screenSaver.setOnClickListener(view -> new CommandSender().execute(ENV_REQUEST_MAPPING, EnvCommand.SCREEN_SAVER));
    }

    private String getIpFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(UserSettings.IP_ADDRESS, "");
    }

    public class CommandSender extends AsyncTask<String, String, String> {

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