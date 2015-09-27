package com.example.mokey.weekport.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ListAdapter;

import com.example.mokey.weekport.R;

/**
 * Created by mokey on 15-9-27.
 */
public class DialogUtil {
    /**
     * @param context
     * @param titleResId
     * @param message
     * @param positiveText
     * @param onPositiveClickListener
     * @param negativeText
     * @param onNegativeClickListener
     * @return
     */
    public static AlertDialog getAlertDialog(
            Context context,
            int titleResId,
            String message,
            String positiveText,
            DialogInterface.OnClickListener onPositiveClickListener,
            String negativeText,
            DialogInterface.OnClickListener onNegativeClickListener
    ) {
        return getAlertDialog(context, context.getString(titleResId), message, positiveText, onPositiveClickListener, negativeText, onNegativeClickListener, true, null);
    }

    /**
     * @param context
     * @param title
     * @param message
     * @param positiveText
     * @param onPositiveClickListener
     * @param negativeText
     * @param onNegativeClickListener
     * @param cancelable
     * @param onCancelListener
     * @return
     */
    public static AlertDialog getAlertDialog(
            Context context,
            String title,
            String message,
            String positiveText,
            DialogInterface.OnClickListener onPositiveClickListener,
            String negativeText,
            DialogInterface.OnClickListener onNegativeClickListener,
            boolean cancelable,
            DialogInterface.OnCancelListener onCancelListener
    ) {
        return new AlertDialog.Builder(context, R.style.appAlertDialogTheme)
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .setCancelable(cancelable)
                .setOnCancelListener(onCancelListener)
                .create();
    }

    /**
     * @param context
     * @param title
     * @param listAdapter
     * @param onClickListener
     * @return
     */
    public static AlertDialog getListAlertDialog(
            Context context,
            String title,
            ListAdapter listAdapter,
            DialogInterface.OnClickListener onClickListener
    ) {
        return getListAlertDialog(context, title, listAdapter, onClickListener, true, null);
    }

    /**
     * @param context
     * @param title
     * @param listAdapter
     * @param onClickListener
     * @return
     */
    public static AlertDialog getListAlertDialog(
            Context context,
            String title,
            ListAdapter listAdapter,
            DialogInterface.OnClickListener onClickListener,
            DialogInterface.OnCancelListener onCancelListener
    ) {
        return getListAlertDialog(context, title, listAdapter, onClickListener, true, onCancelListener);
    }

    /**
     * @param context
     * @param title
     * @param listAdapter
     * @param onClickListener
     * @param cancelable
     * @param onCancelListener
     * @return
     */
    public static AlertDialog getListAlertDialog(
            Context context,
            String title,
            ListAdapter listAdapter,
            DialogInterface.OnClickListener onClickListener,
            boolean cancelable,
            DialogInterface.OnCancelListener onCancelListener
    ) {
        return new AlertDialog.Builder(context, R.style.appAlertDialogTheme)
                .setTitle(title)
                .setAdapter(listAdapter, onClickListener)
                .setCancelable(cancelable)
                .setOnCancelListener(onCancelListener)
                .create();
    }

    /**
     * @param context
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context, String message) {
        return getProgressDialog(context, message, false, null);
    }

    /**
     * @param context
     * @param onCancelListener
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context,
                                                   String message,
                                                   DialogInterface.OnCancelListener onCancelListener) {
        return getProgressDialog(context, message, true, onCancelListener);
    }

    /**
     * @param context
     * @param cancelabe
     * @param onCancelListener
     * @return
     */
    public static ProgressDialog getProgressDialog(Context context,
                                                   String message,
                                                   boolean cancelabe,
                                                   DialogInterface.OnCancelListener onCancelListener) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.mProgress_circle);
        progressDialog.setCancelable(cancelabe);
        progressDialog.setMessage(message);
        progressDialog.setOnCancelListener(onCancelListener);
        return progressDialog;
    }
}
