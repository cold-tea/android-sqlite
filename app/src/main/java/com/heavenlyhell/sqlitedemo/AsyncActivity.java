package com.heavenlyhell.sqlitedemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class AsyncActivity extends AppCompatActivity {

    private EditText etRollDice;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        etRollDice = findViewById(R.id.etRollDice);
        tvResult = findViewById(R.id.tvResultDiceRoll);
        tvResult.setVisibility(View.GONE);
    }

    public void btnRollDice(View view) {
        int number = Integer.parseInt(etRollDice.getText().toString());
        new AsyncDemo(this).execute(number);
    }

    class AsyncDemo extends AsyncTask<Integer, Integer, String> {
        ProgressDialog progressDialog;
        Context context;

        public AsyncDemo(Context context) {
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(Integer.parseInt(etRollDice.getText().toString().trim()));
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            double currentProgress = 0;
            double previousProgress = 0;

            int ones = 0, twos = 0, threes = 0, fours = 0, fives = 0, sixes = 0;
            Random random = new Random();
            for(int i = 0; i<integers[0]; i++) {
                currentProgress = (double) i / integers[0];
                if (currentProgress - previousProgress >= 0.2) {
                    publishProgress(i);
                    previousProgress = currentProgress;
                }


                int result = random.nextInt(6) + 1;
                switch (result) {
                    case 1:
                        ones++;
                        break;
                    case 2:
                        twos++;
                        break;
                    case 3:
                        threes++;
                        break;
                    case 4:
                        fours++;
                        break;
                    case 5:
                        fives++;
                        break;
                    default:
                        sixes++;
                }
            }
            String result = "One: " + ones + "\n" +
                    "Two: " + twos + "\n" +
                    "Three: " + threes + "\n" +
                    "Four: " + fours + "\n" +
                    "Five: " + fives + "\n" +
                    "Six: " + sixes;

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText(result);
            Message.displayMessage(context, "Roll Completed");
        }
    }
}
