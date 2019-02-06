package com.example.vishwas.project_1;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class MainActivity extends AppCompatActivity {

    protected Button enterNameBtn;
    protected  Button openContactsBtn;
    protected static boolean resultOk=false;  //to store the result code validation
    protected  static String name = new String(); //to store the name received from second activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterNameBtn = (Button)findViewById(R.id.enterNameBtn); //enter name button instance
        openContactsBtn =(Button) findViewById(R.id.openContactsBtn); // open contacts button instance

        //sets onclick listeners which are defined below
        enterNameBtn.setOnClickListener(enterNameBtnClick);
        openContactsBtn.setOnClickListener(openContactsBtnClick);
    }

    public View.OnClickListener enterNameBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivityForResult(intent,1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        name = data.getStringExtra("name");

        if(resultCode==RESULT_OK) //set static variable resultOk based on the result code received
        {
            resultOk=true;
        }
        else if(resultCode==RESULT_CANCELED)
            resultOk=false;

    };


    public View.OnClickListener openContactsBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(resultOk) // if resultOk is received, then create a new intent to pass the name to the default contacts app
            {
                Intent contact = new Intent(ContactsContract.Intents.Insert.ACTION);
                contact.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                contact.putExtra(ContactsContract.Intents.Insert.NAME,name);
                startActivity(contact);
            }
            else
            {   //calls toast to display the error message
                Toast.makeText(getApplicationContext(), "Entered name is invalid: "+name, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
