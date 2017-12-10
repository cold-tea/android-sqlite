package com.heavenlyhell.sqlitedemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sandesh on 12/9/2017.
 */

public class Message {
    public static void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
