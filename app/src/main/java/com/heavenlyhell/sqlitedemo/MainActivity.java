package com.heavenlyhell.sqlitedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SandyDbAdapter sandyDbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sandyDbAdapter = new SandyDbAdapter(this);

    }

    public void btnCreateClick(View view) {
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);


        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.length() == 0) {
            etUsername.setError("Usename is required");
            return;
        }
        else if (password.length() == 0) {
            etPassword.setError("Password is required");
            return;
        }
        if (sandyDbAdapter.insert(username, password) < 0)
            Message.displayMessage(this, "Some Problem!");
        else
            Message.displayMessage(this, "Success!");
    }

    public void btnSelectClick(View view) {
        Cursor cursor = sandyDbAdapter.select();
        if(cursor.moveToFirst()) {
            do {
                String username = cursor.getString(1);
                String password = cursor.getString(2);
                Message.displayMessage(this, "Username: " + username + " Password: "+
                    password);
            } while (cursor.moveToNext());
        }
    }

    public void btnLauchActivity(View view) {
        Intent intent = new Intent(this, SharedPrefActivity.class);
        startActivity(intent);
    }

    public void btnAsyncLaunch(View view) {
        Intent intent = new Intent(this, AsyncActivity.class);
        startActivity(intent);
    }
}
