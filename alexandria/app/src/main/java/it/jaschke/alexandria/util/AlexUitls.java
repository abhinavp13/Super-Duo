package it.jaschke.alexandria.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

/**
 * This class holds logic for static util functions used throughout application.
 *
 * Created by pabhinav
 */
public class AlexUitls {

    /** Overriding instantiation for this class **/
    private AlexUitls(){}

    /**
     * This function checks for internet connectivity given activity context
     *
     * @param context
     * @return boolean value denoting connectivity presence.
     */
    public static boolean checkConnectivity(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (int i = 0; info!=null && i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * This function hides keyboard given activity context to it.
     *
     * @param context
     */
    public static void hideKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null){
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
