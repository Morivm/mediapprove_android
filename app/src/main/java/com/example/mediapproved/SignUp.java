package com.example.mediapproved;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    private Button _btnregister;
    private EditText _txtfirstname, _txtmiddlename, _txtlastname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        _txtfirstname = (EditText)findViewById(R.id.txtfirstname);
        _txtmiddlename = (EditText)findViewById(R.id.txtmiddlename);
        _txtlastname = (EditText)findViewById(R.id.txtlastname);
        _btnregister = (Button) findViewById(R.id.btnRegister);

        _btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastname = _txtlastname.getText().toString();
                String firstname = _txtfirstname.getText().toString();
                String middlename = _txtmiddlename.getText().toString();
                String type ="reg";
                BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                backgroundTask.execute(type, lastname, firstname, middlename) ;
            }
        });

    }
}