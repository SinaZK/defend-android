package com.defend.android.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.defend.android.MyApp;
import com.defend.android.Network.NetworkManager;
import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.data.DataStore;
import com.defend.android.utils.ResourceManager;
import com.defend.android.utils.Utils;

import org.json.JSONObject;

public class LoginActivity extends Activity {

    EditText userEditText, passEditText;
    TextView newTextView;
    Button loginButton;

    private String TAG = "_LoginActivity";

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
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLoginRequest();
            }
        });

        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUpActivity();
            }
        });

        String username = getIntent().getStringExtra(Constants.EXTRA_LOGIN_USER);
        if(username == null) username = "";
        String password = getIntent().getStringExtra(Constants.EXTRA_LOGIN_PASSWORD);
        if(password == null) password = "";
        userEditText.setText(username);
        passEditText.setText(password);
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(MyApp.getInstance(), SignUpActivity.class);
        MyApp.getInstance().startActivity(intent);
        finish();
    }

    private boolean checkFields() {
        String username = userEditText.getText().toString();
        String password = passEditText.getText().toString();

        if(username.length() < 6) {
            userEditText.setError(getString(R.string.sign_error_user_short));
            return false;
        }

        if(password.length() < 6) {
            passEditText.setError(getString(R.string.sign_error_pass_short));
            return false;
        }

        return true;
    }

    private void sendLoginRequest() {
        if(!checkFields()) {
            return;
        }

        String url = Constants.API_URL + Constants.API_LOGIN;
        JSONObject object = new JSONObject();
        try {
            object.put("username", userEditText.getText().toString());
            object.put("password", passEditText.getText().toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        Log.i(TAG, object.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                handleOk(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showToast(getString(R.string.login_error));
                userEditText.setError(getString(R.string.login_error));
                passEditText.setError(getString(R.string.login_error));
            }
        });
        NetworkManager.getInstance().sendRequest(request);
    }

    private void handleOk(JSONObject response) {
        try {
            String username = response.getString("username");
            String token = response.getString("token");
            DataStore.getInstance().setStringData(Constants.DATA_FIELD_TOKEN, token);
            DataStore.getInstance().setStringData(Constants.DATA_FIELD_USERNAME, username);
            Log.i(TAG, "user = " + username + " token = " + token);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        finishOK();
    }

    private void finishOK() {
        Intent intent = new Intent(MyApp.getInstance(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
