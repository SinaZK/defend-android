package com.defend.android.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

public class LoginActivity extends Activity {

    EditText userEditText, passEditText;
    TextView newTextView;
    Button loginButton;

    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = findViewById(R.id.userText);
        passEditText = findViewById(R.id.passText);
        loginButton = findViewById(R.id.button);
        newTextView = findViewById(R.id.new_user_text);

        initUI();
    }

    private void initUI() {
        ResourceManager.getInstance().decorateEditText(userEditText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(passEditText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateTextView(newTextView, Color.BLACK, Constants.FONT_BOLD);

        loginButton.setBackgroundResource(R.drawable.btn_bg);
        ResourceManager.getInstance().decorateButton(loginButton, Color.WHITE);

        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });

        username = getIntent().getStringExtra(Constants.EXTRA_LOGIN_USER);
        if(username == null) username = "";
        password = getIntent().getStringExtra(Constants.EXTRA_LOGIN_USER);
        if(password == null) password = "";
        userEditText.setText(username);
        passEditText.setText(password);
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(MyApp.getInstance(), SignUpActivity.class);
        MyApp.getInstance().startActivity(intent);
        finish();
    }
}
