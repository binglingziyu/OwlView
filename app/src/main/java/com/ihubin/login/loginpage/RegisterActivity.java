package com.ihubin.login.loginpage;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by hubin on 16/6/1.
 */
public class RegisterActivity extends Activity{

    private EditText fullname,email,password;
    private OwlView2 owlView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        fullname.setInputType(InputType.TYPE_NULL);
        email.setInputType(InputType.TYPE_NULL);
        password.setInputType(InputType.TYPE_NULL);

        owlView2 = (OwlView2) findViewById(R.id.owl_view);
        owlView2.addWatchEditText(fullname);
        owlView2.addWatchEditText(email);
        owlView2.addWatchEditText(password);
    }
}
