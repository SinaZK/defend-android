package com.defend.android.customViews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.utils.ResourceManager;

public class EducationDialog extends Dialog {

    private TextView diplomaTextView, bachTextView, masterTextView, docTextView, postDocTextView;
    public interface onSelectListener {
        void onSelect(String education, String text);
    }

    private onSelectListener listener;

    public EducationDialog(Context context) {
        super(context);
    }

    public void setListener(onSelectListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_education);

        diplomaTextView = findViewById(R.id.diploma);
        bachTextView = findViewById(R.id.bach);
        masterTextView = findViewById(R.id.master);
        docTextView = findViewById(R.id.doc);
        postDocTextView = findViewById(R.id.post_doc);

        ResourceManager.getInstance().decorateTextView(diplomaTextView, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(bachTextView, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(masterTextView, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(docTextView, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(postDocTextView, Color.BLACK);

        diplomaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("diploma", "دیپلم");
            }
        });

        bachTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("bachelour", "کارشناسی");
            }
        });

        masterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("master", "کارشناسی ارشد");
            }
        });

        docTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("doc", "دکتری");
            }
        });

        postDocTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType("post-doc", "پسادکتری");
            }
        });
    }

    private void selectType(String type, String text) {
        if (listener != null) {
            listener.onSelect(type, text);
        }
        dismiss();
    }
}
