package ru.pascalcode.ymremote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    private EditText iptext;

    private Button normas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        iptext = findViewById(R.id.iptext);
        normas = findViewById(R.id.normas);

        iptext.setText(getIpFromSharedPreferences());

        normas.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE).edit();
            editor.putString(UserSettings.IP_ADDRESS, iptext.getText().toString());
            editor.apply();
            this.finish();
        });

    }


    private String getIpFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSettings.PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(UserSettings.IP_ADDRESS,"");
    }
}
