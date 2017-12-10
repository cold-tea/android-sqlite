package com.heavenlyhell.sqlitedemo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SharedPrefActivity extends AppCompatActivity {

    EditText etName, etFirstName, etLastName;
    TextView tvSpMessage, tvResult;

    List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_pref);

        etName = findViewById(R.id.etName);
        tvSpMessage = findViewById(R.id.tvSpMessage);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        tvResult = findViewById(R.id.tvResult);
        persons = new ArrayList<>();

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

    public void btnFileClick(View view) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();

        if (firstName.length() == 0) {
            etFirstName.setError("Provide First Name !");
            return;
        } else if (lastName.length() == 0) {
            etLastName.setError("Provide Last Name !");
            return;
        }

        Person person = new Person(firstName, lastName);
        persons.add(person);

        setToTextView(persons);
    }

    public void setToTextView(List<Person> persons) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : persons) {
            stringBuilder.append(person.getFirstName() + " " + person.getLastName() + "\n");
        }
        tvResult.setText(stringBuilder);
        clearBoth();

    }

    public void writeToTheFile() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Person person : persons) {
            stringBuilder.append(person.getFirstName() + "," + person.getLastName() + "\n");
        }

        try {
            FileOutputStream fos = openFileOutput("file.txt", MODE_PRIVATE);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            bufferedWriter.write(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clearBoth() {
        etFirstName.setText("");
        etLastName.setText("");
    }

    public void btnSaveClick(View view) {
        writeToTheFile();
    }

    public void btnFileLoad(View view) {
        persons.clear();
        try {
            FileInputStream fis = openFileInput("file.txt");
            String line;
            Person person;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            while((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                person = new Person(tokenizer.nextToken(), tokenizer.nextToken());

                persons.add(person);
            }
            setToTextView(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
