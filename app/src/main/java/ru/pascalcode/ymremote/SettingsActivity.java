package ru.pascalcode.ymremote;

import static ru.pascalcode.ymremote.Constant.ENV_REQUEST_MAPPING;
import static ru.pascalcode.ymremote.Constant.PORT;
import static ru.pascalcode.ymremote.Constant.URL_FORMAT;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends Activity {

    private EditText iptext;

    private Button normas;

    private Button restartYM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        iptext = findViewById(R.id.iptext);
        normas = findViewById(R.id.normas);

        restartYM = findViewById(R.id.restartym);

        iptext.setText(getIpFromSharedPreferences());

        normas.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
            editor.putString(UserSettings.IP_ADDRESS, iptext.getText().toString());
            editor.apply();
            this.finish();
        });
        restartYM.setOnClickListener(view -> new CommandSender().execute(ENV_REQUEST_MAPPING, EnvCommand.RESTART_YM));

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
