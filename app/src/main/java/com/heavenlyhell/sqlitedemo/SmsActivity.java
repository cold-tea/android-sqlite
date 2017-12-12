package com.heavenlyhell.sqlitedemo;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity {

    private EditText etMessage;
    private EditText etTelephone;
    private static final int SMS_REQUEST_CODE = 1;
    private final static String SENT = "SMS_SENT", DELIVERED = "SMS_DELIVERED";
    private PendingIntent sentPendingIntent, deliveredPendingIntent;
    private BroadcastReceiver sendBroadcastReceiver, deliveredBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        etMessage = findViewById(R.id.etMessage);
        etTelephone = findViewById(R.id.etTelephone);

        sentPendingIntent = PendingIntent.getBroadcast(SmsActivity.this, 0,
                new Intent(SENT), 0);
        deliveredPendingIntent = PendingIntent.getBroadcast(SmsActivity.this, 0,
                new Intent(DELIVERED), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case RESULT_OK:
                        Message.displayMessage(SmsActivity.this, "Success! sent.");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Message.displayMessage(SmsActivity.this, "Failure! Generic.");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Message.displayMessage(SmsActivity.this, "Failure! No Service.");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Message.displayMessage(SmsActivity.this, "Failure! Null PDU.");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Message.displayMessage(SmsActivity.this, "Failure! Radio Off.");
                        break;
                }
            }
        };

        deliveredBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case RESULT_OK:
                        Message.displayMessage(SmsActivity.this, "Success! Delivered.");
                        break;
                    case RESULT_CANCELED:
                        Message.displayMessage(SmsActivity.this, "Failure! Canceled.");
                        break;
                }
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(sendBroadcastReceiver);
        unregisterReceiver(deliveredBroadcastReceiver);
    }

    public void btnSendClick(View view) {
        if (ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SmsActivity.this, new String[] {Manifest.permission.SEND_SMS},
                    SMS_REQUEST_CODE);
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(etMessage.getText().toString(), null,
                    etTelephone.getText().toString(), sentPendingIntent, deliveredPendingIntent);
        }
    }
}
