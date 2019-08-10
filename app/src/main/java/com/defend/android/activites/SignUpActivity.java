package com.defend.android.activites;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

public class SignUpActivity extends Activity {

    EditText userEditText, passEditText, pass2EditText, emailEditText;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEditText = findViewById(R.id.userText);
        passEditText = findViewById(R.id.passText);
        pass2EditText = findViewById(R.id.pass2Text);
        emailEditText = findViewById(R.id.emailText);
        signUpButton = findViewById(R.id.button);

        initUI();
    }

    @Override
    public void onBackPressed() {
    }

    private void initUI() {
        ResourceManager.getInstance().decorateEditText(userEditText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(passEditText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(pass2EditText, Color.BLACK, Constants.FONT_REGULAR);
        ResourceManager.getInstance().decorateEditText(emailEditText, Color.BLACK, Constants.FONT_REGULAR);

        signUpButton.setBackgroundResource(R.drawable.btn_bg);
        ResourceManager.getInstance().decorateButton(signUpButton, Color.WHITE);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSignUpRequest();
            }
        });
    }

    private boolean checkFields() {
        return true;
    }

    private void sendSignUpRequest() {
        if(!checkFields()) {
            return;
        }

    }
}
