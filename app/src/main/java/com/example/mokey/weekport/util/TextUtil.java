package com.example.mokey.weekport.util;

import android.text.TextUtils;
import android.widget.TextView;

import java.text.MessageFormat;

/**
 * Created by mokey on 15-9-27.
 */
public class TextUtil {

    /**
     * @param textView  文本控件
     * @param text      显示文字
     * @param otherText 备用显示文字
     */
    public static void setText(TextView textView, String text, String otherText) {
        text = TextUtils.isEmpty(text) ? otherText : text;
        setText(textView, text);
    }

    /**
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text) {
        setText(textView, null, new String[]{text});
    }

    /**
     * @param textView
     * @param placeText 例: 姓名：{0}
     * @param text
     */
    public static void setText(TextView textView, String placeText, Object[] text) {
        if (textView != null) {
            if (placeText != null) {
                placeText = MessageFormat.format(placeText, text);
                textView.setText(placeText);
            } else if (text != null && text.length == 1) {
                textView.setText(TextUtils.isEmpty((String) text[0]) ? "" : (String) text[0]);
            }
        }
    }

}
