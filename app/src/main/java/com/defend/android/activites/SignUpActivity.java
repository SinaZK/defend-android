package com.defend.android.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.defend.android.MyApp;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;

import org.json.JSONObject;

public class SignUpActivity extends Activity {

    EditText userEditText, passEditText, pass2EditText, emailEditText;
    Button signUpButton;

    String TAG = "SignUpActivity";

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
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();
        String password2 = pass2EditText.getText().toString();

        if(username.length() < 6) {
            userEditText.setError(getString(R.string.sign_error_user_short));
            return false;
        }

        if(password.length() < 6) {
            passEditText.setError(getString(R.string.sign_error_pass_short));
            return false;
        }

        if(!password.equals(password2)) {
            passEditText.setError(getString(R.string.sign_error_pass_not_match));
            pass2EditText.setError(getString(R.string.sign_error_pass_not_match));
            return false;
        }

        return true;
    }

    private void sendSignUpRequest() {
        if(!checkFields()) {
            return;
        }

        String url = Constants.API_URL + Constants.API_SIGN_UP;
        JSONObject object = new JSONObject();
        try {
            object.put("username", userEditText.getText().toString());
            object.put("password", passEditText.getText().toString());
            object.put("email", emailEditText.getText().toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                finishOK();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(getString(R.string.sign_error_user_exist));
                userEditText.setError(getString(R.string.sign_error_user_exist));

                Log.e(TAG, error.toString());
            }
        });
        NetworkManager.getInstance().sendRequest(request);
    }

    private void finishOK() {
        Utils.showToast(getString(R.string.sign_success));
        Intent intent = new Intent(MyApp.getInstance(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
