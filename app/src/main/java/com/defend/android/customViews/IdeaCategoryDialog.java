package com.defend.android.customViews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.defend.android.R;
import com.defend.android.constants.Constants;
import com.defend.android.utils.ResourceManager;

public class IdeaCategoryDialog extends Dialog {

    private TextView cat1, cat2, cat3;
    private TextView cat11, cat12, cat13, cat14;
    private TextView cat21, cat22, cat23;
    private TextView cat31, cat32;
    public interface onSelectListener {
        void onSelect(String text);
    }

    private onSelectListener listener;

    public IdeaCategoryDialog(Context context) {
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
        setContentView(R.layout.dialog_idea_category);

        cat1 = findViewById(R.id.cat_1);
        cat2 = findViewById(R.id.cat_2);
        cat3 = findViewById(R.id.cat_3);
        cat11 = findViewById(R.id.cat_1_1);
        cat12 = findViewById(R.id.cat_1_2);
        cat13 = findViewById(R.id.cat_1_3);
        cat14 = findViewById(R.id.cat_1_4);
        cat21 = findViewById(R.id.cat_2_1);
        cat22 = findViewById(R.id.cat_2_2);
        cat23 = findViewById(R.id.cat_2_3);
        cat31 = findViewById(R.id.cat_3_1);
        cat32 = findViewById(R.id.cat_3_2);


        ResourceManager.getInstance().decorateTextView(cat1, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(cat2, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(cat3, Color.BLACK, Constants.FONT_BOLD);
        ResourceManager.getInstance().decorateTextView(cat21, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat22, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat23, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat11, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat12, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat13, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat14, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat31, Color.BLACK);
        ResourceManager.getInstance().decorateTextView(cat32, Color.BLACK);

        setOnClick(cat11);
        setOnClick(cat12);
        setOnClick(cat13);
        setOnClick(cat14);
        setOnClick(cat21);
        setOnClick(cat22);
        setOnClick(cat23);
        setOnClick(cat31);
        setOnClick(cat32);
    }

    private void setOnClick(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType(textView.getText().toString());
            }
        });
    }

    private void selectType(String text) {
        if (listener != null) {
            listener.onSelect(text);
        }
        dismiss();
    }
}
