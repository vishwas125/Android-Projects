package com.example.vishwas.project_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {

    protected EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        name = (EditText)findViewById(R.id.name); //stores the editText instance for name

        name.setOnEditorActionListener(OnEnter);

    }

    public  boolean validateName(String name){

        if(name==null || name.trim().isEmpty())
            return false;
        else
        {
            String regx = "^[\\p{L} .'-]+$";  //to check if a string contains more valid words, language insensitive
            Pattern namePatten = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
            Matcher matcher = namePatten.matcher(name);
            if(matcher.find()) //if valid string(s) entered, then check if it contains at least two words
            {
                List<String> words = new ArrayList<String>(Arrays.asList(name.split("\\s")));
                if(words.size()>=2)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }

    }

    public TextView.OnEditorActionListener OnEnter = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


            String enteredName = name.getText().toString().trim();

            Intent sendName = new Intent();
            sendName.putExtra("name",enteredName); //intent and extra to send the entered name back to calling activity

            if(validateName(enteredName))  //set result accordingly
            {
                setResult(RESULT_OK,sendName);
            }
            else{
                setResult(RESULT_CANCELED,sendName);
            }
            finish();
            return true;
        }
    };


}
