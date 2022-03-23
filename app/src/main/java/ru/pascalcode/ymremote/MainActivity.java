package ru.pascalcode.ymremote;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.pascalcode.ymremote.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TextView textView;

    private Button playPause;

    private Button previous;

    private Button next;

    private Button dislike;

    private Button like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textView = findViewById(R.id.logo);
        playPause = findViewById(R.id.playPause);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        dislike = findViewById(R.id.dislike);
        like = findViewById(R.id.like);
        playPause.setOnClickListener(view -> {
            String url = "http://192.168.0.5:1620/yandexmusic/playpause";
            new CommandSender().execute(url);
        });
        previous.setOnClickListener(view -> {
            String url = "http://192.168.0.5:1620/yandexmusic/previous";
            new CommandSender().execute(url);
        });
        next.setOnClickListener(view -> {
            String url = "http://192.168.0.5:1620/yandexmusic/next";
            new CommandSender().execute(url);
        });
        dislike.setOnClickListener(view -> {
            String url = "http://192.168.0.5:1620/yandexmusic/dislike";
            new CommandSender().execute(url);
        });
        like.setOnClickListener(view -> {
            String url = "http://192.168.0.5:1620/yandexmusic/like";
            new CommandSender().execute(url);
        });

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
                URL url = new URL(strings[0]);
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
            textView.setText(result);
        }
    }
}