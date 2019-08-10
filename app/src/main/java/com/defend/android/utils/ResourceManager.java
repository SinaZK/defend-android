package com.defend.android.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.defend.android.MyApp;
import com.defend.android.R;
import com.defend.android.constants.Constants;

public class ResourceManager {

    private static ResourceManager instance;

    private Typeface font, fontBold, fontLight;

    private ResourceManager() {
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
            instance.loadFonts();
        }

        return instance;
    }

    /**
     * Loads defined fonts: Regular/Bold/Light
     */
    private void loadFonts() {
        Context context = MyApp.getInstance();
        font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANYekanRegularMobile(FaNum).ttf");
        fontBold = Typeface.createFromAsset(context.getAssets(), "fonts/IRANYekanMobileBold(FaNum).ttf");
        fontLight = Typeface.createFromAsset(context.getAssets(), "fonts/IRANYekanLightMobile(FaNum).ttf");
    }

    /**
     * @return Regular font
     */
    public Typeface getFont() {
        return font;
    }

    /**
     * @return Bold font
     */
    public Typeface getFontBold() {
        return fontBold;
    }

    /**
     * @return Light font
     */
    public Typeface getFontLight() {
        return fontLight;
    }

    /**
     * Changes TextView's text color
     *
     * @param textView TextView to change text color
     * @param color    Color
     */
    public void decorateTextView(TextView textView, int color) {
        textView.setTextColor(color);
    }

    /**
     * Changes TextView's text color and font
     *
     * @param textView  TextView to change text color
     * @param color     Color
     * @param FONT_TYPE Constants.FONT_REGULAR/Constants.FONT_BOLD/Constants.FONT_LIGHT
     */
    public void decorateTextView(TextView textView, int color, int FONT_TYPE) {
        textView.setTextColor(color);
        if (FONT_TYPE == Constants.FONT_REGULAR) {
            textView.setTypeface(font);
        } else if (FONT_TYPE == Constants.FONT_BOLD) {
            textView.setTypeface(fontBold);
        } else if (FONT_TYPE == Constants.FONT_LIGHT) {
            textView.setTypeface(fontLight);
        }
    }

    /**
     * Changes EditText's text color and font
     *
     * @param editText  EditText to change text color
     * @param color     Color
     * @param FONT_TYPE Constants.FONT_REGULAR/Constants.FONT_BOLD/Constants.FONT_LIGHT
     */
    public void decorateEditText(EditText editText, int color, int FONT_TYPE) {
        editText.setTextColor(color);
        if (FONT_TYPE == Constants.FONT_REGULAR) {
            editText.setTypeface(font);
        } else if (FONT_TYPE == Constants.FONT_BOLD) {
            editText.setTypeface(fontBold);
        } else if (FONT_TYPE == Constants.FONT_LIGHT) {
            editText.setTypeface(fontLight);
        }
    }

    /**
     * Changes Button's text color
     *
     * @param button EditText to change text color
     * @param color  Color
     */
    public void decorateButton(Button button, int color) {
        button.setTextColor(color);
    }

    /**
     * Changes Button's text color and font
     *
     * @param button    Button to change text color
     * @param color     Color
     * @param FONT_TYPE Constants.FONT_REGULAR/Constants.FONT_BOLD/Constants.FONT_LIGHT
     */
    public void decorateButton(Button button, int color, int FONT_TYPE) {
        button.setTextColor(color);
        if (FONT_TYPE == Constants.FONT_REGULAR) {
            button.setTypeface(font);
        } else if (FONT_TYPE == Constants.FONT_BOLD) {
            button.setTypeface(fontBold);
        } else if (FONT_TYPE == Constants.FONT_LIGHT) {
            button.setTypeface(fontLight);
        }
    }

    /**
     * Sets error to EditText and decorates its text using main font
     *
     * @param editText    EditText to set error
     * @param errorString Error text
     */
    public void createErrorSpan(EditText editText, String errorString) {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorString);
        ssbuilder.setSpan(new CustomTypeFaceSpan("", getFont()),
                0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        editText.setError(ssbuilder);
    }

    /**
     * Sets error to TextView and decorates its text using main font
     *
     * @param textView    TextView to set error
     * @param errorString Error text
     */
    public void createErrorSpan(TextView textView, String errorString) {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorString);
        ssbuilder.setSpan(new CustomTypeFaceSpan("", getFont()),
                0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setError(ssbuilder);
    }

    /**
     * Sets error to EditText and decorates its text using main font
     *
     * @param editText   EditText to set error
     * @param hintString Hint text
     */
    public void createHintSpan(EditText editText, String hintString) {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(hintString);
        ssbuilder.setSpan(new CustomTypeFaceSpan("", getFont()),
                0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        editText.setHint(ssbuilder);
    }

    /**
     * Sets error to TextInputLayout and decorates its text using main font
     *
     * @param inputText  TextInputLayout to set error
     * @param hintString Hint text
     */
    public void createHintSpan(TextInputLayout inputText, String hintString) {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(hintString);
        ssbuilder.setSpan(new CustomTypeFaceSpan("", getFont()),
                0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        inputText.setHint(ssbuilder);
    }

}