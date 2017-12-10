package com.heavenlyhell.sqlitedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPrefActivity extends AppCompatActivity {

    EditText etName;
    TextView tvSpMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        etName = findViewById(R.id.etName);
        tvSpMessage = findViewById(R.id.tvSpMessage);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String tvResult = sharedPreferences.getString("name", "Default");
        tvSpMessage.setText(tvResult);
    }

    public void btnPrefClick(View view) {
        if(etName.getText().length() <= 0) {
            etName.setError("Provide value ..");
            return;
        }
        SharedPreferences.Editor editor = getSharedPreferences("sharedPref", MODE_PRIVATE).edit();
        editor.putString("name", etName.getText().toString());
        editor.commit();
        tvSpMessage.setText(etName.getText().toString());
    }
}
