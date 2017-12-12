package com.heavenlyhell.sqlitedemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Button btnAskPermission = findViewById(R.id.btnAskPermission);
        btnAskPermission.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PermissionActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);

            } else {
                Message.displayMessage(getApplicationContext(), "Permission Granted! Thank You !");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Message.displayMessage(getApplicationContext(), "Thank You! Permission Granted !");
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PermissionActivity.this);
                dialog.setTitle("Grant Permission").setMessage("We need this permission to be able to perform our task adequately.");
                dialog.setPositiveButton("OK", (dialogInterface, i) -> {
                    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE);
                });

                dialog.setNegativeButton("NO THANKS!", (dialogInterface, i) -> {
                    Message.displayMessage(getApplicationContext(), "Permission is not granted!");
                });
                dialog.show();
            } else {
                Message.displayMessage(PermissionActivity.this, "We will never show this dialog.");
            }

        }

    }
}
