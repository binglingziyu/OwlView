package com.ihubin.login.loginpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private OwlView owlView;
    private EditText email;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        owlView = (OwlView) findViewById(R.id.owl_view);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_login);
        password.setOnFocusChangeListener(focusChangeListener);
        login.setOnClickListener(loginListener);
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //if(v.getId() == R.id.password){
                if(hasFocus){
                    owlView.open();
                    //Toast.makeText(MainActivity.this,"获取焦点",Toast.LENGTH_SHORT).show();
                }else{
                    owlView.close();
                    //Toast.makeText(MainActivity.this,"失去焦点",Toast.LENGTH_SHORT).show();
                }
            //}
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"我要登录了!!",Toast.LENGTH_SHORT).show();
        }
    };
}
