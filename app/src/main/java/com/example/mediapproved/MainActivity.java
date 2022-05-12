package com.example.mediapproved;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button _btnLogin;
    private EditText _txtusername, _txtpassword;
    private TextView _txttosignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _txtusername = (EditText)findViewById(R.id.txtusername);
        _txtpassword = (EditText)findViewById(R.id.txtpassword);
        _txttosignup = (TextView)findViewById(R.id.txttosignup);

        _btnLogin = (Button) findViewById(R.id.btnLogin);




        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = _txtusername.getText().toString();
                String password = _txtpassword.getText().toString();
                String type ="login";
                MainActivityTask mainActivityTask = new MainActivityTask(getApplicationContext());
                mainActivityTask.execute(type, username, password);
//                Toast.makeText(getApplicationContext(),username + " " + password,Toast.LENGTH_SHORT).show();
//
            }
        });

        _txttosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUp();


//                Toast.makeText(getApplicationContext(),"DONT CLICK ME",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSignUp() {

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

}