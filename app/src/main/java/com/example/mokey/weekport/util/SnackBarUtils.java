package com.example.mokey.weekport.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.mokey.weekport.R;

/**
 * 创建SnackBar，设置显示的文本颜色
 * Created by monkey on 2015/8/11.
 */
public class SnackBarUtils {
    /**
     * 创建SnackBar并个性显示的文本颜色
     *
     * @param view
     * @param message
     */
    public static Snackbar makeSnackBar(View view,
                                        String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView mMessageView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
        TextView mActionView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_action);
        mActionView.setTextColor(view.getResources().getColor(R.color.colorAccent));
        mMessageView.setTextColor(view.getResources().getColor(R.color.textColorWhite));
        return snackbar;
    }

    /**
     * @param view
     * @param message
     * @param actoinTitle
     * @param onClickListener
     */
    public static Snackbar makeSnackBar(View view,
                                        String message,
                                        String actoinTitle,
                                        View.OnClickListener onClickListener) {
        Snackbar snackbar = makeSnackBar(view, message);
        snackbar.setAction(actoinTitle, onClickListener).show();
        return snackbar;

    }
}
