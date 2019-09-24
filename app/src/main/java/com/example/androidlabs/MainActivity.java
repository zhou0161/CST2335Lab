package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    EditText editText;
    String previous = "UserEmailFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        editText = findViewById(R.id.userEmail);

        Button loginBtn = findViewById(R.id.loginButton);

        prefs = getSharedPreferences(previous, MODE_PRIVATE);
        previous = prefs.getString("userEmail", "");

        editText.setText(previous);

        loginBtn.setOnClickListener(c -> {
            Intent goProfileActivity = new Intent(MainActivity.this, ProfileActivity.class);

            goProfileActivity.putExtra("userEmail", editText.getText().toString());
//            goProfileActivity.putExtra("email", "emialtest");
//            goProfileActivity.putExtra("name", "nametest");

            startActivityForResult(goProfileActivity, 30);
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userEmail", editText.getText().toString());
        editor.commit();
    }

}
