package com.defend.android.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.data.DataStore;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        done();
    }

    private void done() {
        if (DataStore.getInstance().isLogin()) {
            Intent intent = new Intent(MyApp.getInstance(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApp.getInstance().startActivity(intent);
        } else {
            Intent intent = new Intent(MyApp.getInstance(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApp.getInstance().startActivity(intent);
        }
        finish();
    }
}
